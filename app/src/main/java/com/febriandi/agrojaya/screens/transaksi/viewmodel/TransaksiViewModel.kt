package com.febriandi.agrojaya.screens.transaksi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febriandi.agrojaya.model.TransaksiRequest
import com.febriandi.agrojaya.model.PaymentResponse
import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//viewmodel transaksi
@HiltViewModel
class TransaksiViewModel @Inject constructor(
    private val apiService: ApiService,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _paymentState = MutableStateFlow<Resource<PaymentResponse>>(Resource.Loading)
    val paymentState: StateFlow<Resource<PaymentResponse>> = _paymentState.asStateFlow()

    fun createTransaction(
        paketId: Int,
        namaPaket: String,
        alamatId: Int,
        totalHarga: Int,
        variasiBibit: String
    ) {
        viewModelScope.launch {
            try {
                val currentUser = firebaseAuth.currentUser
                if (currentUser == null) {
                    _paymentState.value = Resource.Error("User tidak terautentikasi")
                    return@launch
                }

                val transaksiRequest = TransaksiRequest(
                    uid = currentUser.uid,
                    nama_pengguna = currentUser.displayName ?: "",
                    email = currentUser.email ?: "",
                    paket_id = paketId,
                    nama_paket = namaPaket,
                    alamat_id = alamatId,
                    total_harga = totalHarga,
                    variasi_bibit = variasiBibit
                )

                val response = apiService.createTransaksi(transaksiRequest)
                if (response.isSuccessful && response.body() != null) {
                    _paymentState.value = Resource.Success(response.body()!!)
                } else {
                    _paymentState.value = Resource.Error("Gagal membuat transaksi")
                }
            } catch (e: Exception) {
                _paymentState.value = Resource.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}