package com.febriandi.agrojaya.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.firebase.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//viewmodel lupa password
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _resetPasswordState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val resetPasswordState: StateFlow<Resource<Boolean>> = _resetPasswordState

    fun updateEmail(email: String) {
        _emailState.value = email
    }

    fun resetPassword() {
        viewModelScope.launch {
            _resetPasswordState.value = Resource.Loading()
            try {
                auth.sendPasswordResetEmail(_emailState.value).await()
                _resetPasswordState.value = Resource.Success(true)
            } catch (e: Exception) {
                _resetPasswordState.value = Resource.Error(
                    message = e.localizedMessage ?: "Gagal mengirim email reset password"
                )
            }
        }
    }

    fun resetState() {
        _resetPasswordState.value = Resource.Loading()
    }
}