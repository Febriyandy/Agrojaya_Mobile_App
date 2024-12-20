package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.UserGoogleRepository
import com.febriandi.agrojaya.data.firebase.Resource
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.model.GoogleUser
import javax.inject.Inject

class UserGoogleRepositoryImpl @Inject constructor(
    private  val apiService: ApiService
) : UserGoogleRepository {
    //Menyimpan data user yang login dengan google
    override suspend fun saveGoogleUser(uid: String, email: String, username: String, fcmToken: String): Resource<GoogleUser> {
        return try {
            val user = GoogleUser(uid = uid, email = email, username = username, fcmToken = fcmToken)
            val response = apiService.createGoogleUser(user)

            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to save user: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}