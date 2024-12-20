package com.febriandi.agrojaya.screens.artikel

import android.util.Log
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

@HiltViewModel
class ArtikelViewModel @Inject constructor(
    private val repository: ArtikelRepository
) : ViewModel() {

    private val _artikelState = MutableStateFlow<Resource<List<ArtikelResponse>>>(Resource.Loading)
    val artikelState: StateFlow<Resource<List<ArtikelResponse>>> = _artikelState

    private var originalArtikels: List<ArtikelResponse> = emptyList()

    private val _searchResultState = MutableStateFlow<List<ArtikelResponse>>(emptyList())
    val searchResultState: StateFlow<List<ArtikelResponse>> = _searchResultState


    init {
        loadArtikels()
    }

    //viewmodel mendapatkan data artikel
    fun loadArtikels() {
        viewModelScope.launch {
            _artikelState.value = Resource.Loading
            try {
                val result = repository.getArtikels()
                if (result is Resource.Success) {
                    originalArtikels = result.data
                }
                _artikelState.value = result
            } catch (e: Exception) {
                _artikelState.value = Resource.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    //viewmodel mencari artikel
    fun searchArtikelshome(query: String) {
        viewModelScope.launch {
            Log.d("ArtikelViewModel", "Searching with query: $query")

            // Log total artikel yang tersedia
            val artikelsToSearch = when {
                originalArtikels.isNotEmpty() -> {
                    Log.d("ArtikelViewModel", "Using originalArtikels, size: ${originalArtikels.size}")
                    originalArtikels
                }
                _artikelState.value is Resource.Success -> {
                    val data = (_artikelState.value as Resource.Success).data ?: emptyList()
                    Log.d("ArtikelViewModel", "Using artikelState, size: ${data.size}")
                    data
                }
                else -> {
                    Log.d("ArtikelViewModel", "No articles available")
                    emptyList()
                }
            }


            artikelsToSearch.forEachIndexed { index, artikel ->
                Log.d("ArtikelViewModel", "Artikel $index: Judul = ${artikel.judul}")
            }

            val filteredArtikels = artikelsToSearch.filter { artikel ->
                val judulMatch = artikel.judul.contains(query, ignoreCase = true)
                val isiMatch = artikel.isi.contains(query, ignoreCase = true)

                if (judulMatch || isiMatch) {
                    Log.d("ArtikelViewModel", "Match found: ${artikel.judul}")
                }

                judulMatch || isiMatch
            }

            Log.d("ArtikelViewModel", "Filtered articles count: ${filteredArtikels.size}")
            _searchResultState.value = filteredArtikels
        }
    }

    //viewmodel mencari artikel
    fun searchArtikels(query: String) {
        viewModelScope.launch {
            try {
                if (query.isEmpty()) {
                    _artikelState.value = Resource.Success(originalArtikels)
                } else {
                    val filteredList = originalArtikels.filter { artikel ->
                        artikel.judul.contains(query, ignoreCase = true) ||
                                artikel.isi.contains(query, ignoreCase = true)
                    }
                    _artikelState.value = Resource.Success(filteredList)
                }
            } catch (e: Exception) {
                _artikelState.value = Resource.Error("Terjadi kesalahan saat mencari artikel")
            }
        }
    }
}