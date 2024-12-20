package com.febriandi.agrojaya.data.Repository

import com.febriandi.agrojaya.model.Kabupaten
import com.febriandi.agrojaya.model.Kecamatan
import com.febriandi.agrojaya.model.Kelurahan
import com.febriandi.agrojaya.model.Provinsi

//LocationRepository
interface LocationRepository {
    suspend fun getProvinsi(): Result<List<Provinsi>>
    suspend fun getKabupaten(provinsiId: String): Result<List<Kabupaten>>
    suspend fun getKecamatan(kabupatenId: String): Result<List<Kecamatan>>
    suspend fun getKelurahan(kecamatanId: String): Result<List<Kelurahan>>
}
