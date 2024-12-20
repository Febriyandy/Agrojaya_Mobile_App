package com.febriandi.agrojaya.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.febriandi.agrojaya.model.Pengingat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

//Menyimpan data pengingat di room
@Dao
interface PengingatDao {
    @Query("SELECT * FROM pengingat WHERE tanggal = :tanggal")
    fun getDaftarPengingatByTanggal(tanggal: LocalDate): Flow<List<Pengingat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun tambahPengingat(pengingat: Pengingat)

    @Update
    suspend fun perbaruidPengingat(pengingat: Pengingat)

    @Delete
    suspend fun hapusPengingat(pengingat: Pengingat)

    @Query("SELECT * FROM pengingat WHERE id = :id")
    fun getPengingatById(id: Int): Flow<Pengingat>
}