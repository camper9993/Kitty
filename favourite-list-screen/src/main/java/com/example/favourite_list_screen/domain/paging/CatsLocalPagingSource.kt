package com.example.favourite_list_screen.domain.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data_source.local.ImagesDatabase
import com.example.data_source.model.persistence.Image
import com.example.favourite_list_screen.domain.usecase.GetFavouriteUseCase
import com.example.utils.constants.Constants
import javax.inject.Inject

/**
 * Fetches a certain page from the favourite cat images API and stores it in the database. The load
 * function is called by the paging library.
 */
@OptIn(ExperimentalPagingApi::class)
class CatsLocalPagingSource @Inject constructor(
    private val useCase: GetFavouriteUseCase,
    private val imagesDatabase: ImagesDatabase
) : RemoteMediator<Int, Image>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {
        try {
            val loadPage = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.let {
                    state.pages.size
                } ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = useCase.buildUseCase(GetFavouriteUseCase.Params(
                userId = Constants.USER_ID,
                page = loadPage,
                pageSize = Constants.PAGE_SIZE
            ))
            imagesDatabase.imagesDao().insertAll(response)

            return MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

}