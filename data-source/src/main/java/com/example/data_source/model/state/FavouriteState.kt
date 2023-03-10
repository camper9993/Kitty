package com.example.data_source.model.state

sealed class FavouriteState {
    data class Load(val position: Int? = null) : FavouriteState()
    data class Error(val message: String? = null) : FavouriteState()
}