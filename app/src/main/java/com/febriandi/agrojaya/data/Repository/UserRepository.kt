package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.data.firebase.Resource
import com.febriandi.agrojaya.model.User

//User Repository
interface UserRepository {
    suspend fun createUser(uid: String, username: String, email: String, fcmToken: String): Resource<User>
    suspend fun updateFCMToken(uid: String, token: String): Resource<Unit>
}