package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.Pengingat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

//PengingatRepository
interface PengingatRepository {
    fun getDaftarPengingatByTanggal(tanggal: LocalDate): Flow<List<Pengingat>>
    suspend fun tambahPengingat(pengingat: Pengingat)
    suspend fun perbaruidPengingat(pengingat: Pengingat)
    suspend fun hapusPengingat(pengingat: Pengingat)
    fun getPengingatById(id: Int): Flow<Pengingat>
}