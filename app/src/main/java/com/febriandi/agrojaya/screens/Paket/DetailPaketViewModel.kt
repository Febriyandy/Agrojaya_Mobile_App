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

//Detail paket viewmodel
@HiltViewModel
class DetailPaketViewModel @Inject constructor(
    private val repository: PaketRepository
) : ViewModel() {
    private val _paketState = MutableStateFlow<Resource<PaketResponse>>(Resource.Loading)
    val paketState: StateFlow<Resource<PaketResponse>> = _paketState

    //mendapatkan data paket berdasarkan id
    fun loadPaket(id : Int) {
        viewModelScope.launch {
            _paketState.value = Resource.Loading
            try {
                _paketState.value = repository.getPaketById(id)
            } catch (e: Exception) {
                _paketState.value = Resource.Error(e.message ?: "Terjadi Kesalahan")
            }
        }
    }
}