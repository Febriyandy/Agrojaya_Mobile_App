package com.febriandi.agrojaya.screens.Paket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.PaketRepository
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//view model paket
@HiltViewModel
class PaketViewModel @Inject constructor(
    private val repository: PaketRepository
) : ViewModel() {

    private val _paketState = MutableStateFlow<Resource<List<PaketResponse>>>(Resource.Loading)
    val paketState: StateFlow<Resource<List<PaketResponse>>> = _paketState

    private var originalPakets: List<PaketResponse> = emptyList()

    init {
        loadPakets()
    }

    //mendapatkan data semua paket
    fun loadPakets() {
        viewModelScope.launch {
            _paketState.value = Resource.Loading
            try {
                val result = repository.getPakets()
                when (result) {
                    is Resource.Success -> {
                        originalPakets = result.data
                        _paketState.value = result
                    }
                    is Resource.Error -> {
                        _paketState.value = Resource.Error(result.message ?: "Terjadi Kesalahan")
                    }
                    else -> {
                        _paketState.value = Resource.Error("Terjadi Kesalahan")
                    }
                }
            } catch (e: Exception) {
                _paketState.value = Resource.Error(e.message ?: "Terjadi Kesalahan")
            }
        }
    }
}