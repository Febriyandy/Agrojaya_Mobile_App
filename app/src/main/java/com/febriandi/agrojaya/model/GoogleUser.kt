package com.febriandi.agrojaya.model

//Googel User
data class GoogleUser(
    val uid: String,
    val email: String,
    val username: String,
    val photoUrl: String? = null,
    val fcmToken: String
)
