package com.novina.amazeme.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novina.amazeme.data.network.mapper.toModel
import com.novina.amazeme.model.Result
import com.novina.amazeme.model.Show
import com.novina.amazeme.data.repository.ShowsRepository
import javax.inject.Inject

class ShowsPagingSource @Inject constructor(
    private val repository: ShowsRepository
) : PagingSource<Int, Show>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            when (val result = repository.loadShows(page)) {
                is Result.Error -> LoadResult.Error(result.throwable)
                is Result.Success -> {
                    LoadResult.Page(
                        data = result.data.map { it.toModel(page) },
                        prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                        nextKey = page + 1
                    )
                }
            }
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
