package com.febriandi.agrojaya.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

//Pengingat
@Entity(tableName = "pengingat")
data class Pengingat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jam: String,
    val tanggal: LocalDate,
    val catatan: String
)