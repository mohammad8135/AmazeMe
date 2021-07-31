package com.novina.amazeme.domain

import com.novina.amazeme.data.model.Result
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.data.network.toShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetShowUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {

    operator fun invoke(id: Int): Flow<Result<Show>> {

        // Return flow to have consistent UI and extensibility
        return flow {
            val showResult = when (val result = showsRepository.getShow(id)) {
                is Result.Success -> {
                    val show = result.data.toShow()
                    Result.Success(show)
                }
                is Result.Error -> {
                    result
                }
            }
            emit(showResult)
        }
    }
}
