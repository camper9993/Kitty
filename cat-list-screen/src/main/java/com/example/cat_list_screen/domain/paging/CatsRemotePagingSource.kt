package com.example.cat_list_screen.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cat_list_screen.domain.usecase.GetRandomPicturesUseCase
import com.example.data_source.model.persistence.Image
import com.example.data_source.repository.CatRepository
import javax.inject.Inject

class CatsRemotePagingSource @Inject constructor(
    private val useCase: GetRandomPicturesUseCase
) : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> = try {
        val loadPage = params.key ?: 0

        val result = useCase.buildUseCase(
            GetRandomPicturesUseCase.Param(
                page = loadPage,
                pageSize = params.loadSize
            )
        )

        LoadResult.Page(
            data = result,
            prevKey = null,
            nextKey = loadPage + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition
    }
}