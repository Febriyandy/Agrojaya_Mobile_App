package com.febriandi.agrojaya.screens.pengingat.notifikasiPengingat

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.febriandi.agrojaya.model.Pengingat
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

//Notifikasi jadwal view model
fun jadwalkanNotifikasi(context: Context, pengingat: Pengingat) {

    val waktuNotifikasi = LocalDateTime.of(
        pengingat.tanggal,
        LocalTime.parse(pengingat.jam.replace(" WIB", ""))
    )


    val delay = Duration.between(LocalDateTime.now(), waktuNotifikasi)


    val inputData = Data.Builder()
        .putString("CATATAN", pengingat.catatan)
        .build()

    val notificationWorkRequest = OneTimeWorkRequestBuilder<PengingatWorker>()
        .setInputData(inputData)
        .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
        .build()


    WorkManager.getInstance(context)
        .enqueue(notificationWorkRequest)
}