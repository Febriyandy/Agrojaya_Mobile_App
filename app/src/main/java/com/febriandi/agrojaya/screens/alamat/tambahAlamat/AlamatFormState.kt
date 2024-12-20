package com.febriandi.agrojaya.screens.alamat.tambahAlamat

import com.febriandi.agrojaya.model.Kabupaten
import com.febriandi.agrojaya.model.Kecamatan
import com.febriandi.agrojaya.model.Kelurahan
import com.febriandi.agrojaya.model.Provinsi

//AlamatForm State
data class AlamatFormState(
    val nama: String,
    val noHp: String,
    val provinsi: Provinsi?,
    val kabupaten: Kabupaten?,
    val kecamatan: Kecamatan?,
    val kelurahan: Kelurahan?,
    val alamat: String,
    val catatan: String
) {
    fun isValid(): Boolean {
        return nama.isNotBlank() &&
                noHp.isNotBlank() &&
                provinsi != null &&
                kabupaten != null &&
                kecamatan != null &&
                kelurahan != null &&
                alamat.isNotBlank()
    }
}

// Interface untuk mendapatkan nama dari model lokasi
interface HasNama {
    val nama: String
}
