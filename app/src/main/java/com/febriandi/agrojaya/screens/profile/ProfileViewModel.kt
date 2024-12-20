package com.febriandi.agrojaya.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.ProfileRepository
import com.febriandi.agrojaya.model.ProfileState
import com.febriandi.agrojaya.model.UserResponse
import com.febriandi.agrojaya.screens.profile.component.ProfileEvent
import com.febriandi.agrojaya.screens.profile.component.UiEvent
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import javax.inject.Inject

//viewmodel profile
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<UserResponse>>(Resource.Loading)
    private val _state = MutableStateFlow(ProfileState())
    private val _profilePhotoUri = MutableStateFlow<String?>(null)

    val state = _state.asStateFlow()
    val userState: StateFlow<Resource<UserResponse>> = _userState
    val profilePhotoUri = _profilePhotoUri.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadUserData()
        loadProfilePhoto()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UpdateProfile -> {
                updateProfile(event.username, event.phoneNumber)
            }
            is ProfileEvent.UpdateProfilePhoto -> {
                updateProfilePhoto(event.uri.toString())
            }
            is ProfileEvent.RefreshProfilePhoto -> {
                loadProfilePhoto()
            }
            is ProfileEvent.RefreshUserData -> {
                loadUserData()
            }
        }
    }

    fun loadProfilePhoto() {
        viewModelScope.launch {
            try {
                repository.getProfilePhoto().collectLatest { photoPath ->
                    if (photoPath != null) {
                        val photoUri = Uri.fromFile(File(photoPath))
                        _profilePhotoUri.value = photoUri.toString()
                        _state.value = _state.value.copy(profilePhotoUri = photoUri.toString())
                    } else {
                        Log.d("ProfileViewModel", "Tidak ada foto profil")
                        _profilePhotoUri.value = null
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading profile photo", e)
                _uiEvent.send(UiEvent.ShowSnackbar("Gagal memuat foto profil"))
                _profilePhotoUri.value = null
            }
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            try {
                val userId = repository.getCurrentUserId()
                _userState.value = repository.getUserById(userId)
            } catch (e: Exception) {
                _userState.value = Resource.Error(e.message ?: "Terjadi Kesalahan")
            }
        }
    }

    fun cancelOngoingOperations() {
        viewModelScope.cancel()
    }

    fun updateProfile(username: String,  phoneNumber: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            repository.updateProfile(username,  phoneNumber)
                .onSuccess {
                    _uiEvent.send(UiEvent.Success)
                    loadUserData() // Refresh user data after successful update
                    _state.value = _state.value.copy(isLoading = false)
                }
                .onFailure { throwable ->
                    _uiEvent.send(UiEvent.ShowSnackbar(
                        throwable.localizedMessage ?: "Gagal memperbarui profil"
                    ))
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }

    private fun updateProfilePhoto(uriString: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            repository.saveProfilePhoto(uriString)
                .onSuccess { savedUri ->
                    _state.value = _state.value.copy(
                        profilePhotoUri = savedUri,
                        isLoading = false
                    )
                    _profilePhotoUri.value = savedUri
                    _uiEvent.send(UiEvent.PhotoUpdateSuccess(savedUri ?: ""))
                }
                .onFailure { throwable ->
                    Log.e("ProfileViewModel", "Gagal upload foto", throwable)
                    _state.value = _state.value.copy(isLoading = false)
                    _uiEvent.send(UiEvent.ShowSnackbar(
                        throwable.localizedMessage ?: "Gagal mengupload foto profil"
                    ))
                }
        }
    }
}