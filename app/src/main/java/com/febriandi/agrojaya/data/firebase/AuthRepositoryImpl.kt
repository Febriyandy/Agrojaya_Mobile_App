package com.febriandi.agrojaya.data.firebase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//AuthRepositoryImpl Firebase
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    override fun loginUser(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                result.user?.let { user ->
                    saveLoginState(true)
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("Login failed: User data is null"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return flow {
            emit(Resource.Loading())
            try {
                if (email.isBlank() || password.length < 6) {
                    throw IllegalArgumentException("Invalid email or password")
                }
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                result.user?.let { user ->
                    saveLoginState(true)
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("Registration failed: User data is null"))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Registration Failed"))
            }
        }
    }

    override fun loginWithGoogle(credential: AuthCredential): Flow<Resource<FirebaseUser>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = firebaseAuth.signInWithCredential(credential).await()
                result.user?.let { user ->
                    saveLoginState(true)
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("Google login failed: User data is null"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override suspend fun saveLoginState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        saveLoginState(false)
    }

    override fun changePassword(currentPassword: String, newPassword: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                // Validasi password
                if (newPassword.length < 6) {
                    throw IllegalArgumentException("Password minimal 6 karakter")
                }

                // Dapatkan pengguna saat ini
                val currentUser = firebaseAuth.currentUser
                currentUser?.let { user ->
                    // Email untuk re-autentikasi
                    val email = user.email ?: throw Exception("Email tidak tersedia")

                    // Re-autentikasi dengan kredensial email dan password saat ini
                    val credential = EmailAuthProvider.getCredential(email, currentPassword)
                    user.reauthenticate(credential).await()

                    // Update password
                    user.updatePassword(newPassword).await()

                    emit(Resource.Success(true))
                } ?: throw Exception("Pengguna tidak terautentikasi")

            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Gagal mengubah kata sandi"))
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}