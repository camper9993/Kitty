package com.example.data_source.repository

import com.example.data_source.local.ImagesDatabase
import com.example.data_source.model.network.FavouriteRequest
import com.example.data_source.model.persistence.Image
import com.example.data_source.remote.CatApi
import com.example.data_source.remote.toFavImageList
import com.example.data_source.remote.toImageList
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val api: CatApi,
    private val imagesDb: ImagesDatabase
) : CatRepository {
    override suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image> {
        return api.searchImages(page = page, limit = pageSize)
            .toImageList()
            .map {
                val favImage = imagesDb.imagesDao().get(it.id)
                it.copy(favId = favImage?.favId)
            }
    }

    override suspend fun addFavourite(imageId: String, userId: String): String? {
        return api.addFavourite(
            FavouriteRequest(imageId = imageId, subId = userId)
        ).id
    }

    override suspend fun getFavourites(userId: String, page: Int, pageSize: Int): List<Image>{
        return api.getFavourites(
            subId = userId,
            page = page,
            limit = pageSize
        ).toFavImageList()
    }

    override suspend fun removeFavourite(favId: String) {
        api.removeFavourite(favouriteId = favId)
        imagesDb.imagesDao().delete(favId = favId)
    }
}