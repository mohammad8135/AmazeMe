package com.novina.amazeme.util

import android.os.Build
import android.text.Html
import com.novina.amazeme.data.model.Show
import com.novina.amazeme.ui.viewmodel.MAX_RATING


fun Show.ratingString(): String = rating?.let { "$it / $MAX_RATING" } ?: "Not Available"

fun Show.summaryString(): String {
    return summary?.let { htmlSummary ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlSummary, Html.FROM_HTML_MODE_COMPACT).toString()
        } else Html.fromHtml(htmlSummary).toString()
    } ?: ""
}

fun Show.genreString(): String = genres?.let { genres -> genres.joinToString { it } } ?: ""