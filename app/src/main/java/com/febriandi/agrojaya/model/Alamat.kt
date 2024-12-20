package com.febriandi.agrojaya.model

//Alamat request
data class AlamatRequest(
    val uid: String,
    val nama: String,
    val noHp: String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val kelurahan: String,
    val alamatLengkap: String,
    val catatan: String
)

//Base Response
data class BaseResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)

//Alamat Response
data class AlamatResponse(
    val id: Int,
    val uid: String,
    val nama: String,
    val noHp: String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val kelurahan: String,
    val alamatLengkap: String,
    val catatan: String
)

//Alamat Update Request
data class AlamatUpdateRequest(
    val id: Int? = null,
    val uid: String,
    val nama: String,
    val noHp: String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val kelurahan: String,
    val alamatLengkap: String,
    val catatan: String
)