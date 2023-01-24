package com.example.data_source.model.event

import com.example.data_source.model.persistence.Image

sealed class ExploreEvent {
    object Refresh : ExploreEvent()

    data class ImageFavouredChanged(
        val position: Int,
        val image: Image,
        val isFavoured: Boolean
    ) : ExploreEvent()
}