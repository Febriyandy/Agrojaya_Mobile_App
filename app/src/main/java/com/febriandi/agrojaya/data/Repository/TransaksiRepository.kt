package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.PaymentResponse
import com.febriandi.agrojaya.model.PaymentStatus
import com.febriandi.agrojaya.model.TransaksiRequest
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.utils.Resource

//TransaksiRepository
interface TransaksiRepository {
    suspend fun simpanTransaksi(transaksi: TransaksiRequest): Result<PaymentResponse>
    suspend fun getTransaksisByUid(): List<TransaksiResponse>
    suspend fun getTransaksiById(order_id: String): Resource<TransaksiResponse>
    suspend fun  getStatusTransaksi(order_id: String): Resource<PaymentStatus>
}