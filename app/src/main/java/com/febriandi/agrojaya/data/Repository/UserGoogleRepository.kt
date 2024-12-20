package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.data.firebase.Resource
import com.febriandi.agrojaya.model.GoogleUser

//User Google Repository
interface UserGoogleRepository {
    suspend fun saveGoogleUser(uid: String, email: String, username: String, fcmToken: String): Resource<GoogleUser>
}