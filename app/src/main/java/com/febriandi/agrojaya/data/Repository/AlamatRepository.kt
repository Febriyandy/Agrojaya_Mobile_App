package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.AlamatRequest
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.model.AlamatUpdateRequest
import com.febriandi.agrojaya.model.BaseResponse

// AlamayRepository
interface AlamatRepository {
    suspend fun simpanAlamat(alamat: AlamatRequest): Result<BaseResponse>
    suspend fun getAlamatByUid(): List<AlamatResponse>
    suspend fun getAlamatById(id: Int): AlamatResponse
    suspend fun updateAlamat(alamat: AlamatUpdateRequest): Result<BaseResponse>
}