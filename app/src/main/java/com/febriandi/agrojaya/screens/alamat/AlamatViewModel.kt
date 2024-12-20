package com.febriandi.agrojaya.screens.alamat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.AlamatRepository
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlamatViewModel @Inject constructor(
    private val repository: AlamatRepository
) : ViewModel() {

    private val _alamatState = MutableStateFlow<Resource<List<AlamatResponse>>>(Resource.Loading)
    val alamatState: StateFlow<Resource<List<AlamatResponse>>> = _alamatState

    private var originalAlamatList: List<AlamatResponse> = emptyList()

    init {
        loadAlamat()
    }

    //ViewModel Menampilkan alamat
    fun loadAlamat() {
        viewModelScope.launch {
            _alamatState.value = Resource.Loading
            try {
                val alamatList = repository.getAlamatByUid()
                originalAlamatList = alamatList
                _alamatState.value = Resource.Success(alamatList)
            } catch (e: Exception) {
                _alamatState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat memuat alamat")
            }
        }
    }

}