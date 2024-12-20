package com.febriandi.agrojaya.main

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

//AgroJayaApplication
@HiltAndroidApp
class AgroJayaApplication : Application() {

    val appDataStore: DataStore<Preferences>
        get() = dataStore

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}