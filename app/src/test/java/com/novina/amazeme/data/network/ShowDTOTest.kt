package com.novina.amazeme.data.network

import com.novina.amazeme.data.model.Show
import org.junit.Test
import org.junit.Assert.assertEquals

class ShowDTOTest {

    @Test
    fun show_converted() {
        val imagesDTO = ShowImageDTO(
            medium = "https://medium.image",
            original = "https://original.image"
        )

        val ratingDTO = RatingDTO(average = 2.0)

        val showDTO = ShowDTO(
            id = 1,
            name = "Show Name",
            summary = "<p> summary </p>",
            rating = ratingDTO,
            images = imagesDTO,
            genres = listOf("Genre1", "Genre2")
        )

        val show = showDTO.toShow()

        val expectedShow = Show(
            id = 1,
            name = "Show Name",
            summary = "<p> summary </p>",
            rating = 2.0,
            imageUrl = "https://medium.image",
            genres = listOf("Genre1", "Genre2")
        )
        assertEquals(expectedShow, show)
    }
}