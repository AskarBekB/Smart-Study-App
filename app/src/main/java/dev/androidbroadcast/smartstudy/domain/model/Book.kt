package dev.androidbroadcast.smartstudy.domain.model

import androidx.room.Entity

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val thumbnail: String
)
