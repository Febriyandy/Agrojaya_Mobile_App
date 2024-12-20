package com.febriandi.agrojaya.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.UserGoogleRepository
import com.febriandi.agrojaya.data.Repository.UserRepository
import com.febriandi.agrojaya.data.firebase.AuthRepository
import com.febriandi.agrojaya.data.firebase.MyFirebaseMessagingService
import com.febriandi.agrojaya.data.firebase.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//view model login
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val myFirebaseMessagingService: MyFirebaseMessagingService,
    private val repository: AuthRepository,
    private val userRepository: UserRepository,
    private val userGoogleRepository: UserGoogleRepository
) : ViewModel() {
    private val _state = Channel<LoginState>()
    val state = _state.receiveAsFlow()

    private val _stateGoogle = mutableStateOf(LoginGoogleState())
    val stateGoogle: State<LoginGoogleState> = _stateGoogle

    private val _isSignOutSuccessful = mutableStateOf(false)
    val isSignOutSuccessful: State<Boolean> = _isSignOutSuccessful


    val isUserLoggedIn: StateFlow<Boolean> = repository.isUserLoggedIn()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private suspend fun getFCMToken(): String {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            ""
        }
    }

    private suspend fun updateFCMToken(uid: String): Resource<Boolean> {
        return try {
            val fcmToken = getFCMToken()
            val result = userRepository.updateFCMToken(uid, fcmToken)
            Resource.Success(result is Resource.Success)
        } catch (e: Exception) {
            Resource.Error("Gagal memperbarui FCM Token: ${e.message}")
        }
    }

    fun loginUser(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            repository.loginUser(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val uid = result.data?.uid
                        uid?.let {
                            // Update FCM Token
                            when (val tokenResult = updateFCMToken(it)) {
                                is Resource.Success -> {
                                    repository.saveLoginState(true)
                                    _state.send(LoginState(success = "Login Berhasil"))
                                    home()
                                }
                                is Resource.Error -> {
                                    _state.send(LoginState(error = tokenResult.message))
                                }
                                else -> {}
                            }
                        } ?: run {
                            _state.send(LoginState(error = "Gagal mendapatkan User ID"))
                        }
                    }
                    is Resource.Loading -> {
                        _state.send(LoginState(loading = true))
                    }
                    is Resource.Error -> {
                        _state.send(LoginState(error = result.message))
                    }
                }
            }
        }
    }

    fun registerUser(username: String, email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            repository.registerUser(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val uid = result.data?.uid ?: return@collect
                        val fcmToken = getFCMToken()
                        when (val apiResult = userRepository.createUser(uid, username, email, fcmToken)) {
                            is Resource.Success -> {
                                // Update FCM Token setelah create user
                                when (val tokenResult = updateFCMToken(uid)) {
                                    is Resource.Success -> {
                                        repository.saveLoginState(true)
                                        _state.send(LoginState(success = "Register Berhasil"))
                                        home()
                                    }
                                    is Resource.Error -> {
                                        _state.send(LoginState(error = tokenResult.message))
                                    }
                                    else -> {}
                                }
                            }
                            is Resource.Error -> {
                                _state.send(LoginState(error = apiResult.message))
                            }
                            is Resource.Loading -> {
                                _state.send(LoginState(loading = true))
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _state.send(LoginState(loading = true))
                    }
                    is Resource.Error -> {
                        _state.send(LoginState(error = result.message))
                    }
                }
            }
        }
    }

    fun loginWithGoogle(credential: AuthCredential, home: () -> Unit) {
        viewModelScope.launch {
            repository.loginWithGoogle(credential).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val firebaseUser = result.data
                        if (firebaseUser != null) {
                            try {
                                val uid = firebaseUser.uid
                                val email = firebaseUser.email ?: ""
                                val username = firebaseUser.displayName ?: email.substringBefore("@")
                                val fcmToken = getFCMToken()

                                when (val apiResult = userGoogleRepository.saveGoogleUser(uid, email, username, fcmToken)) {
                                    is Resource.Success -> {
                                        // Update FCM Token setelah save Google user
                                        when (val tokenResult = updateFCMToken(uid)) {
                                            is Resource.Success -> {
                                                repository.saveLoginState(true)
                                                _stateGoogle.value = LoginGoogleState(success = firebaseUser)
                                                home()
                                            }
                                            is Resource.Error -> {
                                                _stateGoogle.value = LoginGoogleState(error = tokenResult.message)
                                            }
                                            else -> {}
                                        }
                                    }
                                    is Resource.Error -> {
                                        _stateGoogle.value = LoginGoogleState(error = apiResult.message)
                                    }
                                    is Resource.Loading -> {
                                        _stateGoogle.value = LoginGoogleState(loading = true)
                                    }
                                }
                            } catch (e: Exception) {
                                _stateGoogle.value = LoginGoogleState(error = e.message)
                            }
                        } else {
                            _stateGoogle.value = LoginGoogleState(error = "Failed to get user data")
                        }
                    }
                    is Resource.Loading -> {
                        _stateGoogle.value = LoginGoogleState(loading = true)
                    }
                    is Resource.Error -> {
                        _stateGoogle.value = LoginGoogleState(error = result.message)
                    }
                }
            }
        }
    }


    fun signOut(onSignOutComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.signOut()
                repository.saveLoginState(false)
                _isSignOutSuccessful.value = true
                onSignOutComplete()
            } catch (e: Exception) {
                _isSignOutSuccessful.value = false
                _state.send(LoginState(error = "Sign out failed: ${e.message}"))
            }
        }
    }

}
