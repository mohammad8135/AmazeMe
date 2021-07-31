package com.novina.amazeme.data.model

import android.os.Build
import android.os.Parcelable
import android.text.Html
import com.novina.amazeme.ui.showlist.MAX_RATING
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: Int,
    val name: String,
    val summary: String? = null,
    val imageUrl: String? = null,
    val rating: Double? = null,
    val genres: List<String>? = null,
    val page: Int? = 0
) : Parcelable

fun Show?.ratingString(): String = this?.rating?.let { "$it / $MAX_RATING" } ?: "Not Available"

fun Show?.summaryString(): String {
    return this?.summary?.let { htmlSummary ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlSummary, Html.FROM_HTML_MODE_COMPACT).toString()
        } else Html.fromHtml(htmlSummary).toString()
    } ?: ""
}

fun Show?.genreString(): String = this?.genres?.let { genres -> genres.joinToString { it } } ?: ""