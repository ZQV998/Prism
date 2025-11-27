package com.liuzk.prism.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Experience(
    @Json(name = "id")
    val id: String,
    @Json(name = "author")
    val username: String,
    @Json(name = "download_url")
    val coverUrl: String,
    val title: String = "",
    val avatarUrl: String = "",
    val likes: Int = 0,
    val isLiked: Boolean = false
)
