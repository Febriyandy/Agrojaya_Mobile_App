package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.AlamatRepository
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.model.AlamatRequest
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.model.AlamatUpdateRequest
import com.febriandi.agrojaya.model.BaseResponse
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlamatRepositoryImpl @Inject constructor(
    private val alamatApiService: ApiService,
    private val firebaseAuth: FirebaseAuth
) : AlamatRepository {
    //Menyimpan Alamat
    override suspend fun simpanAlamat(alamat: AlamatRequest): Result<BaseResponse> = try {
        val response = alamatApiService.simpanAlamat(alamat)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Gagal menyimpan alamat"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    //Mendapatkan alamat berdasarkan uid
    override suspend fun getAlamatByUid(): List<AlamatResponse> {
        val currentUid = firebaseAuth.currentUser?.uid
            ?: throw IllegalStateException("No user logged in")
        return alamatApiService.getAlamatsByUid(currentUid)
    }

    //Mendapatkan alamat berdasarkan id
    override suspend fun getAlamatById(id: Int): AlamatResponse =
        alamatApiService.getAlamatById(id)

    //Mengupdate data alamat
    override suspend fun updateAlamat(alamat: AlamatUpdateRequest): Result<BaseResponse> = try {
        val id = alamat.id ?: throw IllegalArgumentException("ID alamat harus disertakan")

        val response = alamatApiService.updateAlamat(id, alamat)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Gagal memperbarui alamat: ${response.message()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}