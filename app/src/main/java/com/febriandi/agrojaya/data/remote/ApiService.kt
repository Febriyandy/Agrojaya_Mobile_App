package com.febriandi.agrojaya.data.remote

import com.febriandi.agrojaya.model.AlamatRequest
import com.febriandi.agrojaya.model.AlamatResponse
import com.febriandi.agrojaya.model.AlamatUpdateRequest
import com.febriandi.agrojaya.model.ArtikelResponse
import com.febriandi.agrojaya.model.BaseResponse
import com.febriandi.agrojaya.model.GoogleUser
import com.febriandi.agrojaya.model.PaketResponse
import com.febriandi.agrojaya.model.PaymentResponse
import com.febriandi.agrojaya.model.PaymentStatus
import com.febriandi.agrojaya.model.TransaksiRequest
import com.febriandi.agrojaya.model.TransaksiResponse
import com.febriandi.agrojaya.model.UpdateTokenRequest
import com.febriandi.agrojaya.model.UpdateUserRequest
import com.febriandi.agrojaya.model.User
import com.febriandi.agrojaya.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    //Endpoin untuk menyimpan data user ke backend
    @POST("register")
    suspend fun createUser(@Body user: User): Response<User>

    //Endpoin untuk menyimpan data user yang login dengan Google ke backend
    @POST("register")
    suspend fun createGoogleUser(@Body user: GoogleUser): Response<GoogleUser>

    //Endpoin untuk mendapatkan data artikel
    @GET("artikels")
    suspend fun getArtikels(): List<ArtikelResponse>

    //Endpoin untuk mendapatkan artikel berdasarkan id
    @GET("artikel/{id}")
    suspend fun getArtikel(@Path("id") id: Int): ArtikelResponse

    //Endpoin untuk mendapatkan data paket
    @GET("pakets")
    suspend fun getPakets(): List<PaketResponse>

    //Endpoin untuk mendapatkan data paket berdasarkan id
    @GET("paket/{id}")
    suspend fun getPaket(@Path("id") id: Int): PaketResponse

    //Endpoin untuk mengirimkan data alamat ke backend
    @POST("alamat")
    suspend fun simpanAlamat(@Body alamat: AlamatRequest): Response<BaseResponse>

    //Endpoin untuk mendapatkan data alamat berdasarkan uid
    @GET("alamat/byuid/{uid}")
    suspend fun getAlamatsByUid(@Path("uid") uid: String): List<AlamatResponse>

    //Endpoin untuk mendapatkan data alamat berdasarkan id
    @GET("alamat/byid/{id}")
    suspend fun getAlamatById(@Path("id") id: Int): AlamatResponse

    //Endpoin untuk mengirimkan data transaksi ke backend
    @POST("transaksi")
    suspend fun createTransaksi(@Body transaksi: TransaksiRequest): Response<PaymentResponse>

    //Endpoin untuk mendapatkan data transaksi berdasarkan uid
    @GET("transaksi/byuid/{uid}")
    suspend fun getTransaksisByUid(@Path("uid") uid: String): List<TransaksiResponse>

    //Endpoin untuk mendapatkan data transkasi berdasrkan order id
    @GET("transaksi/byid/{order_id}")
    suspend fun getTransaksiById(@Path("order_id") order_id: String): TransaksiResponse

    //Endpoin untuk mendapatkan status transaksi berdasarkan order id
    @GET("transaksi/status/{order_id}")
    suspend fun getStatusTransaksi(@Path("order_id") order_id: String): PaymentStatus

    //Endpoin untuk mendapatkan data users berdasarkan id
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): UserResponse

    //Endpoin untuk mengupdate data user di backend
    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body request: UpdateUserRequest): Response<Unit>

    //Endpoin untuk mengupdate data FCM Token user di backend
    @PUT("users/token/{uid}")
    suspend fun updateUserToken(@Path("uid") uid: String, @Body request: UpdateTokenRequest)

    //Endpoin untuk mengupdate data alamat di backend
    @PUT("alamat/{id}")
    suspend fun updateAlamat(@Path("id") id: Int, @Body alamat: AlamatUpdateRequest): Response<BaseResponse>
}