package com.novina.amazeme.data.network.mapper

import com.novina.amazeme.model.Show
import com.novina.amazeme.data.network.entity.ShowDTO

fun ShowDTO.toModel(page: Int? = 0) = Show(
    id = id,
    name = name,
    summary = summary,
    imageUrl = images?.medium,
    genres = genres,
    rating = rating?.average,
    page = page
)