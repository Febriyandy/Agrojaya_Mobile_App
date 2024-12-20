package com.febriandi.agrojaya.screens.login
import com.google.firebase.auth.FirebaseUser

//Login Google State
data class LoginGoogleState(
    val success: FirebaseUser? = null,
    val error: String? = "",
    val loading: Boolean = true
)

//Login Email Password State
data class LoginState(
    val success: String? = "",
    val error: String? = "",
    val loading: Boolean = false
)