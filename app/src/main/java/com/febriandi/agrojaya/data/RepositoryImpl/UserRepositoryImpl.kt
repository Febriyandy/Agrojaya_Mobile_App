package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.UserRepository
import com.febriandi.agrojaya.data.firebase.Resource
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.model.UpdateTokenRequest
import com.febriandi.agrojaya.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    //Menyimpan data user yang melakukan register dengan email an password
    override suspend fun createUser(uid: String, username: String, email: String, fcmToken: String): Resource<User> {
        return try {
            val user = User(uid, username, email, fcmToken)
            val response = apiService.createUser(user)

            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Failed to create user: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error creating user: ${e.message}")
        }
    }

    //Mengupdate data FCMToken user di backend
    override suspend fun updateFCMToken(uid: String, token: String): Resource<Unit> {
        return try {
            val request = UpdateTokenRequest(token)
            apiService.updateUserToken(uid, request)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}