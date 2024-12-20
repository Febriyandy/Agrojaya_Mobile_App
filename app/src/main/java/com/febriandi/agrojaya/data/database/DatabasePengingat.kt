package com.febriandi.agrojaya.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.febriandi.agrojaya.data.dao.DateConverter
import com.febriandi.agrojaya.data.dao.PengingatDao
import com.febriandi.agrojaya.model.Pengingat

//Membuat database untuk menyiman Pengingat di room
@Database(entities = [Pengingat::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class PengingatDatabase : RoomDatabase() {
    abstract fun pengingatDao(): PengingatDao

    companion object {
        @Volatile
        private var INSTANCE: PengingatDatabase? = null
    }
}