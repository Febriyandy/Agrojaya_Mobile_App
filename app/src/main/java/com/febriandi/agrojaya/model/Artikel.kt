package com.febriandi.agrojaya.model

//Artikel Response
data class ArtikelResponse(
    val id: Int,
    val judul: String,
    val penulis: String,
    val tanggal: String,
    val isi: String,
    val photo : String,
    val like: Int,
)
