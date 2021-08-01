package com.novina.amazeme.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.model.Result
import com.novina.amazeme.data.network.entity.RatingDTO
import com.novina.amazeme.data.network.entity.ShowDTO
import com.novina.amazeme.data.network.entity.ShowImageDTO
import com.novina.amazeme.data.network.ShowRemoteDataSource
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class ShowsRepositoryTest {

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

    private val shows = listOf(showDTO1, showDTO2)

    private val dataSource: ShowRemoteDataSource = mock()
    private val repository = ShowsRepository(dataSource)


    @Test
    fun loadShows_withSuccess() = runBlocking {
        // Given a list of shows returned for a specific page
        whenever(dataSource.loadShows(1)).thenReturn(Result.Success(shows))

        // When requesting shows
        val data = repository.loadShows(1)

        // Then the correct data is returned
        Assert.assertEquals(Result.Success(shows), data)
    }

    @Test
    fun loadShows_withError() = runBlocking {
        // Given that an error is returned for a specific page
        val result = Result.Error(IOException("error"))
        whenever(dataSource.loadShows(1)).thenReturn(result)

        // When loading shows
        val data = repository.loadShows(1)

        // Then error is returned
        assertTrue(data is Result.Error)
    }

    @Test
    fun getShow_whenLoadSucceeded() = runBlocking {
        // Given that a load has been performed successfully and data cached
        whenever(dataSource.loadShows(1)).thenReturn(Result.Success(shows))
        repository.loadShows(1)

        // When getting a show by id
        val result = repository.getShow(shows[0].id)

        // Then it is successfully retrieved
        assertNotNull(result)
        assertTrue(result is Result.Success)
        assertEquals(shows[0], (result as Result.Success).data)
    }

    @Test
    fun getShow_whenLoadFailed() = runBlocking {
        // Given that a search fails so no data is cached
        whenever(dataSource.loadShows(1)).thenReturn(Result.Error(IOException("Error")))
        repository.loadShows(1)

        // When getting a show by id
        val result = repository.getShow(shows[0].id)

        // Then error is returned
        assertNotNull(result)
        assertTrue(result is Result.Error)
    }
}
