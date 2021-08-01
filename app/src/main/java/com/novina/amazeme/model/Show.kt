package com.novina.amazeme.model

import android.os.Parcelable
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