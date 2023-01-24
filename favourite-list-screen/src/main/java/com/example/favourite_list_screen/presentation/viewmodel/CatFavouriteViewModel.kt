package com.example.favourite_list_screen.presentation.viewmodel

import android.R.id.button3
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.data_source.local.ImagesDatabase
import com.example.data_source.model.persistence.Image
import com.example.downloader.dowloader.DownloadFileWorker
import com.example.favourite_list_screen.domain.paging.CatsLocalPagingSource
import com.example.favourite_list_screen.domain.usecase.GetFavouriteUseCase
import com.example.favourite_list_screen.domain.usecase.RemoveFavouriteUseCase
import com.example.utils.constants.Constants
import com.example.utils.ext.tryOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class CatFavouriteViewModel @Inject constructor(
    private val downloadManager: DownloadFileWorker,
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase,
    private val imagesDatabase: ImagesDatabase,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val catFavouriteImages = Pager(
        config = PagingConfig(pageSize = Constants.PAGE_SIZE),
        remoteMediator = CatsLocalPagingSource(getFavouriteUseCase, imagesDatabase),
    ) {
        imagesDatabase.imagesDao().pagingSource()
    }.flow.cachedIn(viewModelScope)

    fun downloadImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadManager.onDownload(
                fileName = image.id + ".png",
                desc = "Kitty",
                url = image.url
            )
        }
    }

    fun onImageFavouredChanged(image: Image) {
        val favId = image.favId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                removeFavouriteUseCase.buildUseCase(RemoveFavouriteUseCase.Params(favId))
                image.favId = null
            } catch (e: Exception) {
            }
        }
    }

//    private fun getBitmapFromUrl(url: String): Bitmap? {
//        return tryOrNull {
//            return@tryOrNull BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
//        }
//    }

//    private fun temp(context: Context) {
//        val downloadManager = getSystemService<Any>(Context.DOWNLOAD_SERVICE) as DownloadManager?
//        val uri: Uri = Uri.parse(a)
//        val request = DownloadManager.Request(uri)
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        val reference: Long = downloadManager.enqueue(request)
//    }


}