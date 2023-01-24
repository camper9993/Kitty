package com.example.cat_list_screen.domain.di

import com.example.cat_list_screen.domain.paging.CatsRemotePagingSource
import com.example.cat_list_screen.domain.usecase.AddFavouriteUseCase
import com.example.cat_list_screen.domain.usecase.GetRandomPicturesUseCase
import com.example.cat_list_screen.domain.usecase.RemoveFavouriteUseCase
import com.example.data_source.repository.CatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CatListModule {

    @Provides
    fun provideRemoveFavouriteUseCase(repository: CatRepository): RemoveFavouriteUseCase {
        return RemoveFavouriteUseCase(repository)
    }

    @Provides
    fun provideAddFavouriteUseCase(repository: CatRepository): AddFavouriteUseCase {
        return AddFavouriteUseCase(repository)
    }

    @Provides
    fun provideGetRandomPicturesUseCase(repository: CatRepository): GetRandomPicturesUseCase {
        return GetRandomPicturesUseCase(repository)
    }

    @Provides
    fun provideCatsRemotePagingSource(getRandomPicturesUseCase: GetRandomPicturesUseCase): CatsRemotePagingSource {
        return CatsRemotePagingSource(getRandomPicturesUseCase)
    }
}