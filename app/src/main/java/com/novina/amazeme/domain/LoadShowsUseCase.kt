package com.novina.amazeme.domain

import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.model.Result
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.data.network.toShow
import javax.inject.Inject


/**
 * Use case that loads shows from [ShowsRepository].
 */
class LoadShowsUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {

    suspend operator fun invoke(page: Int): Result<List<Show>> {
        val result = showsRepository.loadShows(page)
        return when (result) {
            is Result.Success -> {
                val shows = result.data.map { it.toShow(page) }
                Result.Success(shows)
            }
            is Result.Error -> {
                result
            }
        }
    }
}