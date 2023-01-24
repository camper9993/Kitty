package com.example.data_source.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavouriteResponse(
    @Json(name = "id") val id: String?
)