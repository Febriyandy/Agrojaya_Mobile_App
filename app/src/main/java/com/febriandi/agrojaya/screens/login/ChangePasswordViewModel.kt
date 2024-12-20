package com.febriandi.agrojaya.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.firebase.AuthRepository
import com.febriandi.agrojaya.data.firebase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//View model ganti password
@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentPassword = MutableStateFlow("")
    val currentPassword = _currentPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _changePasswordState = MutableStateFlow<Resource<Boolean>?>(null)
    val changePasswordState = _changePasswordState.asStateFlow()

    fun updateCurrentPassword(password: String) {
        _currentPassword.value = password
    }

    fun updateNewPassword(password: String) {
        _newPassword.value = password
    }

    fun updateConfirmPassword(password: String) {
        _confirmPassword.value = password
    }

    fun changePassword() {
        viewModelScope.launch {
            if (newPassword.value != confirmPassword.value) {
                _changePasswordState.value = Resource.Error("Konfirmasi password tidak cocok")
                return@launch
            }

            authRepository.changePassword(
                currentPassword.value,
                newPassword.value
            ).collect { result ->
                _changePasswordState.value = result
            }
        }
    }

    fun resetState() {
        _changePasswordState.value = null
    }
}