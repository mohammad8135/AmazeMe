package com.novina.amazeme.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novina.amazeme.data.model.Result
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.network.toShow
import com.novina.amazeme.data.repository.ShowsRepository

private const val STARTING_PAGE_INDEX = 1

class ShowsPagingSource(
    private val repository: ShowsRepository
) : PagingSource<Int, Show>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            when (val result = repository.loadShows(page)) {
                is Result.Error -> LoadResult.Error(result.exception)
                is Result.Success -> {
                    LoadResult.Page(
                        data = result.data.map { it.toShow(page) },
                        prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                        nextKey = page + 1
                    )
                }
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
