package com.example.cat_list_screen.domain.usecase

import com.example.data_source.repository.CatRepository
import com.example.utils.base.BaseCoroutineUseCase
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(
    private val repository: CatRepository
): BaseCoroutineUseCase<AddFavouriteUseCase.Params, String?>() {

    override suspend fun buildUseCase(params: Params): String? {
        return repository.addFavourite(params.imageId, params.userId)
    }

    data class Params(val imageId: String, val userId: String)
}