package com.example.data_source.model.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_cat_images")
data class Image(
    @PrimaryKey val id: String,
    val url: String,
    var favId: String? = null
)