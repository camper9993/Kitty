package com.example.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_source.model.persistence.Image

@Database(entities = [Image::class], version = 1, exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao

}