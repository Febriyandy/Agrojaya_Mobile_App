package com.febriandi.agrojaya.data.di

import com.febriandi.agrojaya.data.remote.ApiService
import com.febriandi.agrojaya.data.remote.LocationApiService
import com.febriandi.agrojaya.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

//Modul depedensi injection untuk terhubung ke api dengan retrofit
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //Baseurl API data
    @Provides
    @Singleton
    @Named("BaseUrl")
    fun provideBaseUrl(): String = Constant.BASE_URL

    //Url API data wilayah Indonesia
    @Provides
    @Singleton
    @Named("LocationBaseUrl")
    fun provideLocationBaseUrl(): String = Constant.LOCATION_BASE_URL

    @Provides
    @Singleton
    @Named("DefaultOkHttpClient")
    fun provideDefaultOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("LocationOkHttpClient")
    fun provideLocationOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("DefaultRetrofit")
    fun provideDefaultRetrofit(
        @Named("BaseUrl") baseUrl: String,
        @Named("DefaultOkHttpClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("LocationRetrofit")
    fun provideLocationRetrofit(
        @Named("LocationBaseUrl") baseUrl: String,
        @Named("LocationOkHttpClient") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @Named("DefaultRetrofit") retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationApiService(
        @Named("LocationRetrofit") retrofit: Retrofit
    ): LocationApiService {
        return retrofit.create(LocationApiService::class.java)
    }
}