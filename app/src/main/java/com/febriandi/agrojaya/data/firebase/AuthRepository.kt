package com.febriandi.agrojaya.data.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

//AuthRepository firebase
interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<FirebaseUser>>
    fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser>>
    fun loginWithGoogle(credential: AuthCredential): Flow<Resource<FirebaseUser>>
    suspend fun saveLoginState(isLoggedIn: Boolean)
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun signOut()
    fun changePassword(currentPassword: String, newPassword: String): Flow<Resource<Boolean>>
}