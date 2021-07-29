package com.novina.amazeme.data.network

import retrofit2.Response
import java.io.IOException
import com.novina.amazeme.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowRemoteDataSource @Inject constructor(private val service: TvMazeApiService) {
    suspend fun loadShows(page: Int): Result<List<ShowDTO>> {
        return try {
            val response = service.loadShows(page)
            getResult(response = response, onError = {
                Result.Error(
                    IOException("Error getting shows ${response.code()} ${response.message()}")
                )
            })
        } catch (e: Exception) {
            Result.Error(IOException("Error getting shows, ${e.message}"))
        }
    }

    private inline fun getResult(
        response: Response<List<ShowDTO>>,
        onError: () -> Result.Error
    ): Result<List<ShowDTO>> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }
}