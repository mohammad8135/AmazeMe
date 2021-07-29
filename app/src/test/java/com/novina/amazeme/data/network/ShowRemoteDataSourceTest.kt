package com.novina.amazeme.data.network

import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.data.model.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.net.UnknownHostException

class ShowRemoteDataSourceTest {

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

    private val service: TvMazeApiService = mock()
    private val dataSource = ShowRemoteDataSource(service)
    private val errorResponseBody = "Error".toResponseBody("".toMediaTypeOrNull())


    @Test
    fun loadShows_withSuccess() = runBlocking {
        // Given that the service responds with success
        withShowsSuccess(1, shows)

        // When requesting shows
        val result = dataSource.loadShows(1)

        // Then there's one request to the service
        verify(service).loadShows(1)
        // Then the correct list of shows is returned
        assertEquals(Result.Success(shows), result)
    }

    @Test
    fun loadShows_withError() = runBlocking {
        // Given that the service responds with error
        withShowsError(1)

        // When requesting shows
        val result = dataSource.loadShows(1)

        // Then error is returned
        assertTrue(result is Result.Error)
    }

    @Test
    fun loadShows_withException() = runBlocking {
        // Given that the service throws an exception
        doAnswer { throw UnknownHostException() }
            .whenever(service).loadShows(1)

        // When requesting shows
        val result = dataSource.loadShows(1)

        // Then error is returned
        assertTrue(result is Result.Error)
    }

    private suspend fun withShowsSuccess(page: Int, shows: List<ShowDTO>) {
        val result = Response.success(shows)
        whenever(service.loadShows(page)).thenReturn(result)
    }

    private suspend fun withShowsError(page: Int) {
        val result = Response.error<List<ShowDTO>>(
            400,
            errorResponseBody
        )
        whenever(service.loadShows(page)).thenReturn(result)
    }
}