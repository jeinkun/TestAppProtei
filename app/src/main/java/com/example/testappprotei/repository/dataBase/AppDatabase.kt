package com.example.testappprotei.repository.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testappprotei.repository.dataBase.dao.AlbumsDao
import com.example.testappprotei.repository.dataBase.dao.PhotosDao
import com.example.testappprotei.repository.dataBase.dao.UsersDao
import com.example.testappprotei.repository.dataBase.model.AlbumsEntity
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import com.example.testappprotei.repository.dataBase.model.UsersEntity

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