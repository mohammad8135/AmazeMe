package com.novina.amazeme.data.repository

import com.novina.amazeme.model.Result
import com.novina.amazeme.data.network.entity.ShowDTO
import com.novina.amazeme.data.network.ShowRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowsRepository @Inject constructor(private val remoteDataSource: ShowRemoteDataSource) {

    private val cache = mutableMapOf<Int, ShowDTO>()

    suspend fun loadShows(page: Int) = getData { remoteDataSource.loadShows(page) }

    private suspend fun getData(
        request: suspend () -> Result<List<ShowDTO>>
    ): Result<List<ShowDTO>> {
        val result = request()
        if (result is Result.Success) {
            cache(result.data)
        }
        return result
    }

    fun getShow(id: Int): Result<ShowDTO> {
        val show = cache[id]
        return if (show != null) {
            Result.Success(show)
        } else {
            Result.Error(IllegalStateException("show $id not cached"))
        }
    }

    private fun cache(data: List<ShowDTO>) {
        data.associateTo(cache) { it.id to it }
    }
}