package com.novina.amazeme.data.paging

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.data.network.entity.RatingDTO
import com.novina.amazeme.data.network.entity.ShowDTO
import com.novina.amazeme.data.network.entity.ShowImageDTO
import com.novina.amazeme.data.pagings.source.ShowsPagingSource
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.model.Result
import com.novina.amazeme.model.Show
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ShowsPagingSourceTest {

    private val showDTO1 = ShowDTO(
        id = 1,
        name = "Show 1",
        summary = "<p> summary 1 </p>",
        rating = RatingDTO(average = 1.0),
        images = ShowImageDTO(
            medium = "https://medium.image1",
            original = "https://original.image1"
        ),
        genres = listOf("Genre1", "Genre2")
    )
    private val showDTO2 = ShowDTO(
        id = 2,
        name = "Show 2",
        summary = "<p> summary 2 </p>",
        rating = RatingDTO(average = 2.0),
        images = ShowImageDTO(
            medium = "https://medium.image2",
            original = "https://original.image2"
        ),
        genres = listOf("Genre11", "Genre22")
    )
    private val show1 = Show(
        id = 1,
        name = "Show 1",
        summary = "<p> summary 1 </p>",
        rating = 1.0,
        imageUrl = "https://medium.image1",
        genres = listOf("Genre1", "Genre2"),
        page = 1
    )
    private val show2 = Show(
        id = 2,
        name = "Show 2",
        summary = "<p> summary 2 </p>",
        rating = 2.0,
        imageUrl = "https://medium.image2",
        genres = listOf("Genre11", "Genre22"),
        page = 1
    )

    private val showDTOs = listOf(showDTO1, showDTO2)
    private val shows = listOf(show1, show2)
    private val showsRepository: ShowsRepository = mock()

    @Test
    fun `loadReturnsPage withSuccess`() = runBlockingTest {
        whenever(showsRepository.loadShows(1)).thenReturn(Result.Success(showDTOs))
        val pagingSource = ShowsPagingSource(showsRepository)
        assertEquals(
            PagingSource.LoadResult.Page(
                data = shows,
                prevKey = null,
                nextKey = 2
            ), pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `loadReturnsPage withError`() = runBlockingTest {
        whenever(showsRepository.loadShows(1)).thenReturn(Result.Error(Throwable("Error")))
        val pagingSource = ShowsPagingSource(showsRepository)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 0,
                placeholdersEnabled = false
            )
        )
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}