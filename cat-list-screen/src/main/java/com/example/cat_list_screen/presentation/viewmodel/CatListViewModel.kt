package com.example.cat_list_screen.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cat_list_screen.domain.paging.CatsRemotePagingSource
import com.example.cat_list_screen.domain.usecase.AddFavouriteUseCase
import com.example.cat_list_screen.domain.usecase.GetRandomPicturesUseCase
import com.example.cat_list_screen.domain.usecase.RemoveFavouriteUseCase
import com.example.data_source.model.persistence.Image
import com.example.utils.constants.Constants

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val getRandomPicturesUseCase: GetRandomPicturesUseCase,
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
): ViewModel() {

    val catImagesFlow = Pager(
        pagingSourceFactory = { CatsRemotePagingSource(getRandomPicturesUseCase) },
        config = PagingConfig(pageSize = Constants.PAGE_SIZE)
    ).flow.cachedIn(viewModelScope)


    fun onImageFavouredChanged(image: Image) {
        if (image.favId == null) {
            favourImage(image)
        } else {
            unfavourImage(image)
        }
    }

    private fun favourImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = addFavouriteUseCase.buildUseCase(AddFavouriteUseCase.Params(
                    imageId = image.id,
                    userId = Constants.USER_ID
                ))
                id?.let { image.favId = it }
            } catch (e: Exception) {
//                Log.e(:)
            }
        }
    }

    private fun unfavourImage(image: Image) {
        val favId = image.favId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                removeFavouriteUseCase.buildUseCase(RemoveFavouriteUseCase.Params(favId))
                image.favId = null
            } catch (e: Exception) {
            }
        }
    }

}