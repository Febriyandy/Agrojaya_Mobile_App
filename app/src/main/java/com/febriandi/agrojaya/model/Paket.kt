package com.febriandi.agrojaya.model

//Paket Response
data class PaketResponse(
    val id: Int,
    val nama_paket: String,
    val harga: Int,
    val variasi_bibit : String,
    val fitur: String,
    val detail: String,
    val photo: String,
)
