package com.example.testappprotei.dataBase

import android.content.Context
import androidx.room.Room
import com.example.testappprotei.dataBase.repo.AlbumsDbRepo
import com.example.testappprotei.dataBase.repo.PhotosDbRepo
import com.example.testappprotei.dataBase.repo.UsersDbRepo

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDB: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    val usersDbRepo: UsersDbRepo by lazy { UsersDbRepo(appDB.getUsersDao()) }
    val albumsDbRepo: AlbumsDbRepo by lazy { AlbumsDbRepo(appDB.getAlbumsDao()) }
    val photosDbRepo: PhotosDbRepo by lazy { PhotosDbRepo(appDB.getPhotosDao()) }
}