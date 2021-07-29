package com.novina.amazeme.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.data.network.RatingDTO
import com.novina.amazeme.data.network.ShowDTO
import com.novina.amazeme.data.network.ShowImageDTO
import com.novina.amazeme.data.repository.ShowsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.novina.amazeme.data.model.Result
import junit.framework.TestCase.assertEquals
import java.io.IOException


class LoadShowsUseCaseTest {
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
    private val loadShowsUseCase = LoadShowsUseCase(showsRepository)

    @Test
    fun loadShows_withSuccess() = runBlocking {
        // Given a list of show DTO returned for a specific page
        val expected = Result.Success(showDTOs)
        whenever(showsRepository.loadShows(1)).thenReturn(expected)

        // When loading shows
        val result = loadShowsUseCase(1)

        // Then the result was triggered
        assertEquals(Result.Success(shows), result)
    }

    @Test
    fun loadShows_withError() = runBlocking {
        // Given that an error is returned for a specific page
        val expected = Result.Error(IOException("error"))
        whenever(showsRepository.loadShows(2)).thenReturn(expected)

        // When loading shows
        val result = loadShowsUseCase(2)

        // Then the result was triggered
        assertEquals(expected, result)
    }
}