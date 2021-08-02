package com.novina.amazeme.domain

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.novina.amazeme.model.Show
import com.novina.amazeme.ui.adapter.ShowListAdapter
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test


class LoadShowsUseCaseTest {

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

    private val shows = listOf(show1, show2)
    private val loadShowsUseCase: LoadShowsUseCase = mock()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `loadShows withSuccess`() = testScope.runBlockingTest {
        whenever(loadShowsUseCase.invoke(1)).thenReturn(flow { emit(PagingData.from(shows)) })
        val result = loadShowsUseCase(1).first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ShowListAdapter.ShowDiffCallback(),
            updateCallback = FakeListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(result)
        advanceUntilIdle()
        // Then the result was triggered
        assertEquals(shows, differ.snapshot().items)
    }

    class FakeListCallback : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
}