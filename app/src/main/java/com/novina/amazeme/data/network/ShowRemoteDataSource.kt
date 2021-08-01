package com.novina.amazeme.data.network

import com.novina.amazeme.data.network.api.ApiService
import com.novina.amazeme.model.Result
import com.novina.amazeme.data.network.entity.ShowDTO
import com.novina.amazeme.data.network.error.HttpRequestException
import com.novina.amazeme.util.getDetailedError
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowRemoteDataSource @Inject constructor(private val service: ApiService) {
    suspend fun loadShows(page: Int): Result<List<ShowDTO>> {
        return try {
            val response = service.loadShows(page)
            getResult(response = response, onError = {
                Result.Error(
                    HttpRequestException(
                        message = "${response.code()} ${
                            response.message()
                        }",
                        response = response.toString(),
                        cause = HttpException(response),
                        code = response.code()
                    )
                )
            })
        } catch (e: Throwable) {
            Result.Error(e.getDetailedError())
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