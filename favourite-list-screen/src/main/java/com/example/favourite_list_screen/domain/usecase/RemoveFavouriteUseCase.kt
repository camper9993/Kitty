package com.example.favourite_list_screen.domain.usecase

import com.example.data_source.repository.CatRepository
import com.example.utils.base.BaseCoroutineUseCase
import javax.inject.Inject

class RemoveFavouriteUseCase @Inject constructor(
    private val repository: CatRepository
): BaseCoroutineUseCase<RemoveFavouriteUseCase.Params, Unit>() {

    override suspend fun buildUseCase(params: Params) {
        repository.removeFavourite(params.favId)
    }

    data class Params(val favId: String)

}