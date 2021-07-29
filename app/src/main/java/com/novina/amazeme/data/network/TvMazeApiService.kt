package com.novina.amazeme.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApiService {

    @GET("shows")
    suspend fun loadShows(
        @Query("page") page: Int
    ): Response<List<ShowDTO>>

    companion object{
        private const val BASE_URL = "https://api.tvmaze.com/"

        fun create(): TvMazeApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TvMazeApiService::class.java)
        }
    }
}