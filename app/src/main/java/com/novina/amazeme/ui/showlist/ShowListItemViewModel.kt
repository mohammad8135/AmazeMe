package com.novina.amazeme.ui.showlist

import com.novina.amazeme.data.model.Show

const val MAX_RATING = 10

class ShowListItemViewModel(private val show: Show) {
    val showId: Int
        get() = show.id
    val showName: String
        get() = show.name
    val imageUrl: String?
        get() = show.imageUrl
}