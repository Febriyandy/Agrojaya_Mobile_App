package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.PaketRepository
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.utils.Resource
import javax.inject.Inject

class PaketRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PaketRepository {

    //Mendapatkan data paket
    override suspend fun getPakets(): Resource<List<PaketResponse>> {
        return try {
            val response = apiService.getPakets()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    //Mendapatkan data paket berdasarkan id
    override suspend fun getPaketById(id: Int): Resource<PaketResponse> {
        return try {
            val response = apiService.getPaket(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}