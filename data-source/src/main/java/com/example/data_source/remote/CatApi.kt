package com.example.data_source.remote

import com.example.data_source.model.network.FavouriteItemResponse
import com.example.data_source.model.network.FavouriteRequest
import com.example.data_source.model.network.FavouriteResponse
import com.example.data_source.model.network.ImageResponse
import com.example.utils.constants.Constants
import retrofit2.http.*

interface CatApi {

    @GET("/v1/images/search")
    suspend fun searchImages(
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("page") page: Int = 0,
        @Query("order") order: String = "random"
    ): List<ImageResponse>

    @POST("/v1/favourites")
    suspend fun addFavourite(
        @Body favouriteRequest: FavouriteRequest
    ): FavouriteResponse

    @GET("/v1/favourites")
    suspend fun getFavourites(
        @Query("sub_id") subId: String,
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("page") page: Int = 0
    ): List<FavouriteItemResponse>

    @DELETE("/v1/favourites/{favourite_id}")
    suspend fun removeFavourite(
        @Path("favourite_id") favouriteId: String
    )

}