package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.NotificationRepository
import com.febriandi.agrojaya.model.NotificationModel
import com.febriandi.agrojaya.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) : NotificationRepository {

    //Mendapatkan data notifikasi
    override fun getNotifications(uid: String): Flow<Resource<List<NotificationModel>>> = callbackFlow {

        trySend(Resource.Loading)

        val reference = database.reference.child("notifications").child(uid)
        val listener = reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notifications = snapshot.children.mapNotNull { childSnapshot ->
                    childSnapshot.getValue(NotificationModel::class.java)
                }
                val sortedNotifications = notifications.sortedByDescending { it.waktu }
                trySend(Resource.Success(sortedNotifications))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
                close(error.toException())
            }
        })
        awaitClose { reference.removeEventListener(listener) }
    }

    //Mengahapus data notifikasi berdasarkan id
    override suspend fun deleteNotification(notificationId: String): Resource<Unit> {
        return try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                Resource.Error("Pengguna tidak login")
            } else {
                val reference = database.reference
                    .child("notifications")
                    .child(currentUser.uid)
                    .child(notificationId)

                reference.removeValue().await()
                Resource.Success(Unit)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal menghapus notifikasi")
        }
    }

    //Menghapus semua data notifikasi
    override suspend fun deleteAllNotifications(): Resource<Unit> {
        return try {
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                Resource.Error("Pengguna tidak login")
            } else {
                val reference = database.reference
                    .child("notifications")
                    .child(currentUser.uid)

                reference.removeValue().await()
                Resource.Success(Unit)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Gagal menghapus semua notifikasi")
        }
    }

}