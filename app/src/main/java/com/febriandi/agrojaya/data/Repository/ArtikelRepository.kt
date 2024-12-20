package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.utils.Resource

//ArtikelRepository
interface ArtikelRepository {
    suspend fun getArtikels(): Resource<List<ArtikelResponse>>
    suspend fun getArtikelById(id: Int): Resource<ArtikelResponse>
}