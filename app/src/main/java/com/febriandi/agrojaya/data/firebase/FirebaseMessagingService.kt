package com.febriandi.agrojaya.data.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.febriandi.agrojaya.R
import com.febriandi.agrojaya.data.Repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//FirebaseMessagingService
@AndroidEntryPoint
class MyFirebaseMessagingService @Inject constructor() : FirebaseMessagingService() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    //Menerima notifikasi dari firebase
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM_MESSAGE", "From: ${remoteMessage.from}")

        remoteMessage.data.isNotEmpty().let {
            Log.d("FCM_MESSAGE", "Message data payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let { message ->
            val title = message.title ?: "Judul Kosong"
            val body = message.body ?: "Isi Pesan Kosong"


            Log.d("FCM_MESSAGE", "Notification Title: $title")
            Log.d("FCM_MESSAGE", "Notification Body: $body")

            showNotification(title, body)
        }
    }

    //Menampilkan notifikasi dari firebase
    private fun showNotification(title: String, body: String) {
        val channelId = "FCM_CHANNEL"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.icon)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "FCM Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        Log.d("FCM_MESSAGE", "Showing notification with title: $title")
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }


    fun updateFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val currentUser = firebaseAuth.currentUser

                currentUser?.let { user ->
                    Log.d("FCM_TOKEN", "Updating token for user: ${user.uid}")
                    CoroutineScope(Dispatchers.IO).launch {
                        userRepository.updateFCMToken(user.uid, token)
                    }
                }
            } else {
                Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    //Menyimpan FCM Token ke backend
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", "New token generated: $token")

        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            Log.d("FCM_TOKEN", "Updating token for user: ${user.uid}")
            CoroutineScope(Dispatchers.IO).launch {
                userRepository.updateFCMToken(user.uid, token)
            }
        }
    }
}