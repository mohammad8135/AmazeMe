package com.novina.amazeme.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.novina.amazeme.model.Show
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case that loads shows from [ShowsPagingSource].
 */

class LoadShowsUseCase @Inject constructor(
    private val showsPagingSource: ShowsPagingSource
) {

    operator fun invoke(page: Int): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { showsPagingSource }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 25
    }
}