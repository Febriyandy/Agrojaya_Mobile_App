package com.febriandi.agrojaya.data.Repository


import com.febriandi.agrojaya.model.UserResponse
import com.febriandi.agrojaya.utils.Resource
import kotlinx.coroutines.flow.Flow

//Profile Repository
interface ProfileRepository {
    suspend fun getCurrentUserId(): String
    suspend fun updateProfile(
        username: String,
        phoneNumber: String
    ): Result<Unit>
    suspend fun saveProfilePhoto(uri: String): Result<String?>
    suspend fun getProfilePhoto(): Flow<String?>
    suspend fun getUserById(id: String): Resource<UserResponse>
}