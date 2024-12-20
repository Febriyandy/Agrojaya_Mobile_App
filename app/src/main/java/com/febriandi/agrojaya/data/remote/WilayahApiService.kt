package com.febriandi.agrojaya.data.remote

import com.febriandi.agrojaya.model.Kabupaten
import com.febriandi.agrojaya.model.Kecamatan
import com.febriandi.agrojaya.model.Kelurahan
import com.febriandi.agrojaya.model.Provinsi
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApiService {
    //Endpoin untuk mendapatkan data provinsi
    @GET("provinsi.json")
    suspend fun getProvinsi(): List<Provinsi>

    //Endpoin untuk mendapatkan data kabupaten
    @GET("kabupaten/{provinsiId}.json")
    suspend fun getKabupaten(@Path("provinsiId") provinsiId: String): List<Kabupaten>

    //Endpoin untuk mendapatkan data kecamatan
    @GET("kecamatan/{kabupatenId}.json")
    suspend fun getKecamatan(@Path("kabupatenId") kabupatenId: String): List<Kecamatan>

    //Endpoin untuk mendapatkan data kelurahan
    @GET("kelurahan/{kecamatanId}.json")
    suspend fun getKelurahan(@Path("kecamatanId") kecamatanId: String): List<Kelurahan>
}