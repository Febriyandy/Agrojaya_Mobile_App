package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.PengingatRepository
import com.febriandi.agrojaya.data.dao.PengingatDao
import com.febriandi.agrojaya.model.Pengingat
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class PengingatRepositoryImpl @Inject constructor(
    private val pengingatDao: PengingatDao
) : PengingatRepository {

    //Mendapatkan data pengingat berdasarkan tanggal
    override fun getDaftarPengingatByTanggal(tanggal: LocalDate): Flow<List<Pengingat>> =
        pengingatDao.getDaftarPengingatByTanggal(tanggal)

    //Menambah data pengingat
    override suspend fun tambahPengingat(pengingat: Pengingat) =
        pengingatDao.tambahPengingat(pengingat)

    //Mengupdate data pengingat
    override suspend fun perbaruidPengingat(pengingat: Pengingat) =
        pengingatDao.perbaruidPengingat(pengingat)

    //Mengapus data pengingat
    override suspend fun hapusPengingat(pengingat: Pengingat) =
        pengingatDao.hapusPengingat(pengingat)

    //Mendapatkan data pengingat berdasarkan id
    override fun getPengingatById(id: Int): Flow<Pengingat> =
        pengingatDao.getPengingatById(id)
}