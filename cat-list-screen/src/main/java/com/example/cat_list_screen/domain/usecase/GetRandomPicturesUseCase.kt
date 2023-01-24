package com.example.cat_list_screen.domain.usecase

import com.example.data_source.model.persistence.Image
import com.example.data_source.repository.CatRepository
import com.example.utils.base.BaseCoroutineUseCase
import javax.inject.Inject

class GetRandomPicturesUseCase @Inject constructor(
    private val catRepository: CatRepository
) : BaseCoroutineUseCase<GetRandomPicturesUseCase.Param, List<Image>>() {

    override suspend fun buildUseCase(params: Param): List<Image> {
        return catRepository.getRandomCatImages(params.page, params.pageSize)
    }

    data class Param(val page: Int, val pageSize: Int)
}