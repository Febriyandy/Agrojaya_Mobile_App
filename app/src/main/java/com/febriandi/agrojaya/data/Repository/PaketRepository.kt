package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.utils.Resource

//PaketRepository
interface PaketRepository {
    suspend fun getPakets(): Resource<List<PaketResponse>>
    suspend fun getPaketById(id: Int): Resource<PaketResponse>
}