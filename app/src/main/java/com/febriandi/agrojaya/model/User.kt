package com.febriandi.agrojaya.model

//User
data class User(
    val uid: String,
    val username: String,
    val email: String,
    val fcmToken: String
)

//Profile State
data class ProfileState(
    val profilePhotoUri: String? = null,
    val isLoading: Boolean = false
)

//Update user request
data class UpdateUserRequest(
    val username: String,
    val phoneNumber: String
)

//User Response
data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val phoneNumber: String
)

//Update Token Request
data class UpdateTokenRequest(
    val fcmToken: String
)


