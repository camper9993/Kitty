package com.example.data_source.model.event

import com.example.data_source.model.persistence.Image

sealed class FavouriteEvent {
    object Refresh : FavouriteEvent()

    data class ImageFavouredChanged(
        val position: Int,
        val image: Image,
        val isFavoured: Boolean
    ) : FavouriteEvent()
}