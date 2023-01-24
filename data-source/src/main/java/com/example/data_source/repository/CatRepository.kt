package com.example.data_source.repository

import com.example.data_source.model.persistence.Image


interface CatRepository {
    suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image>
    suspend fun addFavourite(imageId: String, userId: String): String?
    suspend fun getFavourites(userId: String, page: Int, pageSize: Int): List<Image>
    suspend fun removeFavourite(favId: String)
}