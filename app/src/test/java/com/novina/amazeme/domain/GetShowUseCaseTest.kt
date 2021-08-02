package com.novina.amazeme.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.data.network.entity.RatingDTO
import com.novina.amazeme.data.network.entity.ShowDTO
import com.novina.amazeme.data.network.entity.ShowImageDTO
import com.novina.amazeme.data.repository.ShowsRepository
import com.novina.amazeme.util.getFlow
import com.novina.amazeme.model.Result
import com.novina.amazeme.model.Show
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetShowUseCaseTest {

    private val showId = 1
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

    private val show1 = Show(
        id = 1,
        name = "Show 1",
        summary = "<p> summary 1 </p>",
        rating = 1.0,
        imageUrl = "https://medium.image1",
        genres = listOf("Genre1", "Genre2")
    )

    private val showsRepository: ShowsRepository = mock()
    private val getShowUseCase = GetShowUseCase(showsRepository)


    @Test
    fun `loadShows withSuccess`() = runBlocking {
        // Given a list of show DTO returned for a specific page
        whenever(showsRepository.getShow(showId)).thenReturn(Result.Success(showDTO1))

        // When loading show
        val result = getShowUseCase(showId).first()

        // Then the result was triggered
        assertEquals( getFlow(Result.Success(show1)).first(), result)
    }

    @Test
    fun `loadShows withError`() = runBlocking {
        // Given that an error is returned for a specific page
        whenever(showsRepository.getShow(showId)).thenReturn(Result.Error(Throwable("error")))
        // When loading show
        val result = getShowUseCase(showId).first()

        assertTrue(result is Result.Error)
    }

}