package com.novina.amazeme.data.network.api

import com.novina.amazeme.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class ApiFactory @Inject constructor() {

    val apiService: ApiService by lazy {
        createRetrofit(getClient()).create(ApiService::class.java)
    }

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(getConverterFactory())
            .client(httpClient)
            .build()
    }

    private fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(getInterceptor())
        .build()

    private fun getInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private fun getConverterFactory() = GsonConverterFactory.create()

    companion object {
        private const val TIME_OUT: Long = 5
    }
}