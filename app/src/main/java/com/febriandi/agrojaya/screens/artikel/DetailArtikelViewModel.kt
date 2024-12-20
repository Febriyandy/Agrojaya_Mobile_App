package com.febriandi.agrojaya.screens.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.ArtikelRepository
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//Detail Artikel View model
@HiltViewModel
class DetailArtikelViewModel @Inject constructor(
    private val repository: ArtikelRepository
) : ViewModel() {
    private val _artikelState = MutableStateFlow<Resource<ArtikelResponse>>(Resource.Loading)
    val artikelState: StateFlow<Resource<ArtikelResponse>> = _artikelState

    //viewmodel menampilkan artikel berdasarkan id
    fun loadArtikel(id: Int) {
        viewModelScope.launch {
            _artikelState.value = Resource.Loading
            try {
                _artikelState.value = repository.getArtikelById(id)
            } catch (e: Exception) {
                _artikelState.value = Resource.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}