package com.example.favourite_list_screen.di

import android.content.Context
import com.example.data_source.repository.CatRepository
import com.example.downloader.dowloader.DownloadManagerImpl
import com.example.favourite_list_screen.domain.usecase.AddFavouriteUseCase
import com.example.favourite_list_screen.domain.usecase.GetFavouriteUseCase
import com.example.favourite_list_screen.domain.usecase.RemoveFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object FavouriteListModel {

    @Provides
    fun provideRemoveFavouriteUseCase(repository: CatRepository): RemoveFavouriteUseCase {
        return RemoveFavouriteUseCase(repository)
    }

    @Provides
    fun provideAddFavouriteUseCase(repository: CatRepository): AddFavouriteUseCase {
        return AddFavouriteUseCase(repository)
    }

    @Provides
    fun provideGetFavouriteUseCase(repository: CatRepository): GetFavouriteUseCase {
        return GetFavouriteUseCase(repository)
    }

    @Provides
    fun provideDownloadManager(@ApplicationContext context: Context): DownloadManagerImpl {
        return DownloadManagerImpl(context)
    }
}