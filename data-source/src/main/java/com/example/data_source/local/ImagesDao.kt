package com.example.data_source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data_source.model.persistence.Image

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Image>)

    @Query("SELECT * FROM favourite_cat_images")
    fun pagingSource(): PagingSource<Int, Image>

    @Query("DELETE FROM favourite_cat_images")
    suspend fun clearAll()

    @Query("SELECT * FROM favourite_cat_images WHERE id = :imageId LIMIT 1")
    suspend fun get(imageId: String): Image?

    @Query("DELETE FROM favourite_cat_images WHERE favId = :favId")
    suspend fun delete(favId: String)

}