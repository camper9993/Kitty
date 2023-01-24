package com.example.favourite_list_screen.domain.usecase

import com.example.data_source.model.persistence.Image
import com.example.data_source.repository.CatRepository
import com.example.utils.base.BaseCoroutineUseCase
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: CatRepository
): BaseCoroutineUseCase<GetFavouriteUseCase.Params, List<Image>>() {

    override suspend fun buildUseCase(params: Params): List<Image> {
        return repository.getFavourites(params.userId, params.page, params.pageSize)
    }

    data class Params(val userId: String, val page: Int, val pageSize: Int)

}