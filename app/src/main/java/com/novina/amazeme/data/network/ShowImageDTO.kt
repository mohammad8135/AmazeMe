package com.novina.amazeme.data.network

import com.google.gson.annotations.SerializedName

data class ShowImageDTO(
    @field:SerializedName("medium") val medium: String,
    @field:SerializedName("original") val original: String
)
