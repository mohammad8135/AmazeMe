package com.novina.amazeme.domain

import com.novina.amazeme.data.model.Result
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.data.network.toShow
import javax.inject.Inject

class GetShowUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {

    suspend operator fun invoke(id: Int): Result<Show> {
        val result = showsRepository.getShow(id)
        return when (result) {
            is Result.Success -> {
                val show = result.data.toShow()
                Result.Success(show)
            }
            is Result.Error -> {
                result
            }
        }
    }
}
