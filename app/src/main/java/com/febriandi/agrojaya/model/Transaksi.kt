package com.febriandi.agrojaya.model

//Transaksi Request
data class TransaksiRequest(
    val uid: String,
    val nama_pengguna: String,
    val email: String,
    val paket_id: Int,
    val nama_paket: String,
    val alamat_id: Int,
    val total_harga: Int,
    val variasi_bibit: String
)

//Payment Response
data class PaymentResponse(
    val success: Boolean,
    val msg: String,
    val data: TransaksiResponse
)

//Transaksi Response
data class TransaksiResponse(
    val id: Int,
    val order_id: String,
    val uid: String,
    val paket_id: Int,
    val alamat_id: Int,
    val total_harga: Int,
    val variasi_bibit: String,
    val tanggal: String,
    val status_pembayaran: String,
    val status_transaksi: String,
    val snap_token: String,
    val snap_redirect_url: String,
    val nama_paket: String,
    val photo_paket: String,
    val nama_alamat: String,
    val noHp : String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val kelurahan: String,
    val alamatLengkap: String,
    val catatan: String
)

//Payment Status
data class PaymentStatus(
    val order_id: String,
    val gross_amount : String,
    val transaction_status: String,
    val settlement_time: String
)