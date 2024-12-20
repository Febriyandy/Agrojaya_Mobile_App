package com.febriandi.agrojaya.screens.pengingat.notifikasiPengingat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.febriandi.agrojaya.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//Pengingat Work Manager
class PengingatWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val catatan = inputData.getString("CATATAN") ?: return Result.failure()

        createNotification(catatan)
        saveNotificationToFirebase(catatan)

        return Result.success()
    }

    private fun saveNotificationToFirebase(catatan: String) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.let { user ->
            val notificationsRef = database.getReference("notifications/${user.uid}")

            val timestamp = System.currentTimeMillis()

            val notificationData = hashMapOf(
                "title" to "Aktivitas Pertanian Anda Sekarang",
                "body" to catatan,
                "waktu" to timestamp
            )


            val newNotificationRef = notificationsRef.push()


            newNotificationRef.setValue(notificationData)
                .addOnSuccessListener {
                    Log.d("Firebase", "Notifikasi berhasil disimpan")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error menyimpan notifikasi", e)
                }
        }
    }

    private fun createNotification(catatan: String) {
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "PENGINGAT_CHANNEL",
                "Aktivitas Pertanian",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }


        val notification = NotificationCompat.Builder(
            applicationContext,
            "PENGINGAT_CHANNEL"
        )
            .setContentTitle("Aktivitas Pertanian Anda Sekarang")
            .setContentText(catatan)
            .setSmallIcon(R.drawable.icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }
}