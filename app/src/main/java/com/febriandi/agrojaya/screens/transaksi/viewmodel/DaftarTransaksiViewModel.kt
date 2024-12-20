package com.febriandi.agrojaya.screens.transaksi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.TransaksiRepository
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Viewmodel Daftar Transaksi
@HiltViewModel
class DaftarTransaksiViewModel @Inject constructor(
    private val repository: TransaksiRepository
) : ViewModel() {

    private val _transaksiState = MutableStateFlow<Resource<List<TransaksiResponse>>>(Resource.Loading)
    val transaksiState: StateFlow<Resource<List<TransaksiResponse>>> = _transaksiState

    private var originalTransaksis: List<TransaksiResponse> = emptyList()

    init {
        loadTransaksis()
    }

    fun loadTransaksis() {
        viewModelScope.launch {
            _transaksiState.value = Resource.Loading
            try {
                val alamatList = repository.getTransaksisByUid()
                originalTransaksis = alamatList
                _transaksiState.value = Resource.Success(alamatList)
            } catch (e: Exception) {
                _transaksiState.value = Resource.Error(e.message ?: "Terjadi kesalahan saat memuat alamat")
            }
        }
    }
}