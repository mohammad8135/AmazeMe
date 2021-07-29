package com.novina.amazeme.data.network

import com.google.gson.annotations.SerializedName
import com.novina.amazeme.data.model.Show

/**
 * Data class that represents a show response from TV Maze.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below.
 * [here](https://api.tvmaze.com/shows).
 */

data class ShowDTO(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("summary") val summary: String?,
    @field:SerializedName("image") val images: ShowImageDTO?,
    @field:SerializedName("rating") val rating: RatingDTO?,
    @field:SerializedName("genres") val genres: List<String>?,
)

fun ShowDTO.toShow(page: Int? = 0) = Show(
    id = id,
    name = name,
    summary = summary,
    imageUrl = images?.medium,
    genres = genres,
    rating = rating?.average,
    page = page
)

