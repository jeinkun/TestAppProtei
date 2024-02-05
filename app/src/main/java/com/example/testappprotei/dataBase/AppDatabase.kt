package com.example.testappprotei.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testappprotei.dataBase.dao.AlbumsDao
import com.example.testappprotei.dataBase.dao.PhotosDao
import com.example.testappprotei.dataBase.dao.UsersDao
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.dataBase.model.PhotosEntity
import com.example.testappprotei.dataBase.model.UsersEntity

@Database(
    entities = [
        UsersEntity::class,
        AlbumsEntity::class,
        PhotosEntity::class
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

    abstract fun getAlbumsDao(): AlbumsDao

    abstract fun getPhotosDao(): PhotosDao

}