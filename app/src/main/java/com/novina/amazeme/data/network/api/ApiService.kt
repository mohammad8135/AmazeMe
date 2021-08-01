package com.novina.amazeme.data.network.api

import com.novina.amazeme.data.network.entity.ShowDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("shows")
    suspend fun loadShows(
        @Query("page") page: Int
    ): Response<List<ShowDTO>>

}