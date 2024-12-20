package com.febriandi.agrojaya.screens.transaksi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.data.Repository.TransaksiRepository
import com.febriandi.agrojaya.model.PaymentStatus
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//viewmodel detail transaksi
@HiltViewModel
class DetailTransaksiViewModel @Inject constructor(
    private val repository: TransaksiRepository
) : ViewModel() {
    private val _transaksiState = MutableStateFlow<Resource<TransaksiResponse>>(Resource.Loading)
    private val _paymentState = MutableStateFlow<Resource<PaymentStatus>>(Resource.Loading)
    val transaksiState: StateFlow<Resource<TransaksiResponse>> = _transaksiState
    val paymentState: StateFlow<Resource<PaymentStatus>> = _paymentState


    fun loadTransaksi(order_id : String) {
        viewModelScope.launch {
            _transaksiState.value = Resource.Loading
            try {
                _transaksiState.value = repository.getTransaksiById(order_id)
            } catch (e: Exception) {
                _transaksiState.value = Resource.Error(e.message ?: "Terjadi Kesalahan")
            }
        }
    }

    fun loadPaymentStatus(order_id : String) {
        viewModelScope.launch {
            _paymentState.value = Resource.Loading
            try {
                _paymentState.value = repository.getStatusTransaksi(order_id)
            } catch (e: Exception) {
                _paymentState.value = Resource.Error(e.message ?: "Terjadi Kesalahan")
            }
        }
    }
}