package com.example.data_source.di

import android.app.Application
import androidx.room.Room
import com.example.data_source.local.ImagesDatabase
import com.example.data_source.remote.ApiKeyInterceptor
import com.example.data_source.remote.CatApi
import com.example.data_source.repository.CatRepository
import com.example.data_source.repository.CatRepositoryImpl
import com.example.utils.constants.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideCatRepository(catRepository: CatRepositoryImpl): CatRepository

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor(ApiKeyInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun provideOpenFoodApi(client: OkHttpClient): CatApi {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
                .create()
        }

        @Provides
        @Singleton
        fun provideImagesDatabase(app: Application): ImagesDatabase {
            return Room.databaseBuilder(
                app,
                ImagesDatabase::class.java,
                "images_db"
            ).build()
        }
    }
}