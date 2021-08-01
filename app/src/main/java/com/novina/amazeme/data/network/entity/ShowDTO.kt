package com.novina.amazeme.data.network.entity

import com.google.gson.annotations.SerializedName

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

