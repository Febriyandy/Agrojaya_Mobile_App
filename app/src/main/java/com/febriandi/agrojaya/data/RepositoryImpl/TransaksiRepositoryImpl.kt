package com.febriandi.agrojaya.data.RepositoryImpl

import com.febriandi.agrojaya.data.Repository.TransaksiRepository
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.model.PaymentResponse
import com.febriandi.agrojaya.model.PaymentStatus
import com.febriandi.agrojaya.model.TransaksiRequest
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransaksiRepositoryImpl @Inject constructor(
    private val transaksiApiService: ApiService,
    private val firebaseAuth: FirebaseAuth
) : TransaksiRepository {

    //Menyimpan data transaksi
    override suspend fun simpanTransaksi(transaksi: TransaksiRequest): Result<PaymentResponse> = try {
        val response = transaksiApiService.createTransaksi(transaksi)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Gagal melakukan transaksi: ${response.code()} - ${response.message()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    //Mendapatkan data transaksi berdasarkan uid
    override suspend fun getTransaksisByUid(): List<TransaksiResponse> {
        val currentUid = firebaseAuth.currentUser?.uid
            ?: throw IllegalStateException("No user logged in")
        return transaksiApiService.getTransaksisByUid(currentUid)
    }

    //Mendapatkan data transkasi bersarkan id
    override suspend fun getTransaksiById(order_id: String): Resource<TransaksiResponse> {
        return try {
            val response = transaksiApiService.getTransaksiById(order_id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    //Mendapatkan status transaksi
    override suspend fun getStatusTransaksi(order_id: String): Resource<PaymentStatus> {
        return try {
            val response =  transaksiApiService.getStatusTransaksi(order_id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}