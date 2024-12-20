package com.febriandi.agrojaya.data.RepositoryImpl

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.febriandi.agrojaya.data.Repository.ProfileRepository
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.main.AgroJayaApplication
import com.febriandi.agrojaya.model.UpdateUserRequest
import com.febriandi.agrojaya.model.UserResponse
import com.febriandi.agrojaya.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: ApiService,
    @ApplicationContext private val context: Context,
) : ProfileRepository {

    private val dataStore: DataStore<Preferences>
        get() = (context.applicationContext as AgroJayaApplication).appDataStore

    private companion object {
        val PROFILE_PHOTO_KEY = stringPreferencesKey("profile_photo")
    }


    override suspend fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw Exception("User not logged in")
    }

    //Mengupdate data profile
    override suspend fun updateProfile(
        username: String,
        phoneNumber: String
    ): Result<Unit> = try {
        firebaseAuth.currentUser?.let { user ->
            if (!validateInput(username, phoneNumber)) {
                return Result.failure(Exception("Invalid input data"))
            }

            val userId = user.uid
            val response = apiService.updateUser(
                userId = userId,
                UpdateUserRequest(
                    username = username,
                    phoneNumber = phoneNumber
                )
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to update user data"))
            }
        } ?: Result.failure(Exception("User not logged in"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    private fun validateInput(username: String,  phoneNumber: String): Boolean {
        return username.isNotBlank() &&
                phoneNumber.matches(Regex("^\\+?\\d{10,14}$"))
    }

    //Menyimpan photo profile di Data Store
    override suspend fun saveProfilePhoto(uri: String): Result<String?> {
        return try {
            val parsedUri = Uri.parse(uri)

            val context = context.applicationContext
            val inputStream = context.contentResolver.openInputStream(parsedUri)


            val fileName = "profile_photo_${System.currentTimeMillis()}.jpg"
            val outputFile = File(context.filesDir, fileName)

            inputStream?.use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            dataStore.edit { preferences ->
                preferences[PROFILE_PHOTO_KEY] = outputFile.absolutePath
            }

            Result.success(outputFile.absolutePath)
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Gagal menyimpan foto profil", e)
            Result.failure(Exception("Gagal menyimpan foto profil: ${e.localizedMessage}"))
        }
    }

    //Mendapatkan data photo profile
    override suspend fun getProfilePhoto(): Flow<String?> {
        return dataStore.data.map { preferences ->
            val photoPath = preferences[PROFILE_PHOTO_KEY]

            if (photoPath != null) {
                val file = File(photoPath)
                if (file.exists()) {
                    file.absolutePath
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    //Mendapatkan data user berdasarkan uid
    override suspend fun getUserById(id: String): Resource<UserResponse> {
        return try {
            val response = apiService.getUserById(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}