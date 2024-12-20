package com.febriandi.agrojaya.screens.alamat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.AlamatRepository
import com.febriandi.agrojaya.data.Repository.LocationRepository
import com.febriandi.agrojaya.model.AlamatRequest
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.model.AlamatUpdateRequest
import com.febriandi.agrojaya.model.BaseResponse
import com.febriandi.agrojaya.model.Kabupaten
import com.febriandi.agrojaya.model.Kecamatan
import com.febriandi.agrojaya.model.Kelurahan
import com.febriandi.agrojaya.model.Provinsi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TambahAlamatViewModel @Inject constructor(
    private val alamatRepository: AlamatRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _submitResult = MutableSharedFlow<Result<BaseResponse>>()
    val submitResult = _submitResult.asSharedFlow()

    private val _provinsiList = MutableStateFlow<List<Provinsi>>(emptyList())
    val provinsiList: StateFlow<List<Provinsi>> = _provinsiList.asStateFlow()

    private val _kabupatenList = MutableStateFlow<List<Kabupaten>>(emptyList())
    val kabupatenList: StateFlow<List<Kabupaten>> = _kabupatenList.asStateFlow()

    private val _kecamatanList = MutableStateFlow<List<Kecamatan>>(emptyList())
    val kecamatanList: StateFlow<List<Kecamatan>> = _kecamatanList.asStateFlow()

    private val _kelurahanList = MutableStateFlow<List<Kelurahan>>(emptyList())
    val kelurahanList: StateFlow<List<Kelurahan>> = _kelurahanList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _existingAlamat = MutableStateFlow<AlamatResponse?>(null)
    val existingAlamat: StateFlow<AlamatResponse?> = _existingAlamat.asStateFlow()

    init {
        loadProvinsi()
    }

    //viewmodel simpan alamat
    fun simpanAlamat(
        nama: String,
        noHp: String,
        provinsi: String,
        kabupaten: String,
        kecamatan: String,
        kelurahan: String,
        alamatLengkap: String,
        catatan: String
    ) {
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    _submitResult.emit(Result.failure(Exception("User belum login")))
                    return@launch
                }

                val alamatRequest = AlamatRequest(
                    uid = currentUser.uid,
                    nama = nama,
                    noHp = noHp,
                    provinsi = provinsi,
                    kabupaten = kabupaten,
                    kecamatan = kecamatan,
                    kelurahan = kelurahan,
                    alamatLengkap = alamatLengkap,
                    catatan = catatan
                )

                val result = alamatRepository.simpanAlamat(alamatRequest)
                _submitResult.emit(result)
            } catch (e: Exception) {
                _submitResult.emit(Result.failure(e))
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    //viewmodel data provinsi
    fun loadProvinsi() {
        viewModelScope.launch {
            _isLoading.value = true
            locationRepository.getProvinsi()
                .onSuccess { _provinsiList.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    //viewmodel data kabupaten
    fun loadKabupaten(provinsiId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            locationRepository.getKabupaten(provinsiId)
                .onSuccess { _kabupatenList.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    //viewmodel data kecamatan
    fun loadKecamatan(kabupatenId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            locationRepository.getKecamatan(kabupatenId)
                .onSuccess { _kecamatanList.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    //viewmodel data kelurahan
    fun loadKelurahan(kecamatanId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            locationRepository.getKelurahan(kecamatanId)
                .onSuccess { _kelurahanList.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    //viewmodel data alamat by id
    fun loadAlamatById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val alamat = alamatRepository.getAlamatById(id)
                _existingAlamat.value = alamat

                // Muat daftar lokasi bertahap
                loadProvinsi()
                loadKabupaten(alamat.provinsi)
                loadKecamatan(alamat.kabupaten)
                loadKelurahan(alamat.kecamatan)
            } catch (e: Exception) {
                _error.value = "Gagal memuat alamat: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    //viewmodel update alamat
    fun updateAlamat(
        id: Int,
        nama: String,
        noHp: String,
        provinsi: String,
        kabupaten: String,
        kecamatan: String,
        kelurahan: String,
        alamatLengkap: String,
        catatan: String
    ) {
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser == null) {
                    _submitResult.emit(Result.failure(Exception("User belum login")))
                    return@launch
                }

                val alamatRequest = AlamatUpdateRequest(
                    id = id,
                    uid = currentUser.uid,
                    nama = nama,
                    noHp = noHp,
                    provinsi = provinsi,
                    kabupaten = kabupaten,
                    kecamatan = kecamatan,
                    kelurahan = kelurahan,
                    alamatLengkap = alamatLengkap,
                    catatan = catatan
                )

                val result = alamatRepository.updateAlamat(alamatRequest)
                _submitResult.emit(result)
            } catch (e: Exception) {
                _submitResult.emit(Result.failure(e))
            } finally {
                _isSubmitting.value = false
            }
        }
    }
}