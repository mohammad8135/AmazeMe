package com.novina.amazeme.ui.showlist

import android.os.Build
import android.text.Html
import com.novina.amazeme.data.model.Show

const val MAX_RATING = 10

class ShowListItemViewModel(private val show: Show) {
    val showId: Int
        get() = show.id
    val showName: String
        get() = show.name
    val imageUrl: String?
        get() = show.imageUrl


    val rating: String
        get() = show.rating?.let { "$it / $MAX_RATING" } ?: "Not Available"
    val genre: String
        get() = show.genres?.let { genres -> genres.joinToString { it } } ?: ""
    val summary: String
        get() = show.summary?.let { htmlSummary ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlSummary, Html.FROM_HTML_MODE_COMPACT).toString()
            } else Html.fromHtml(htmlSummary).toString()
        } ?: ""
}