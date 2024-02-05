package com.example.testappprotei.dataBase.repo

import com.example.testappprotei.dataBase.dao.AlbumsDao
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.dataBase.model.AlbumsTuple

class AlbumsDbRepo(private val albumsDao: AlbumsDao) {

    suspend fun insertNewAlbumsData(albumsEntity: List<AlbumsEntity>) {
        albumsDao.insertNewAlbumsData(albumsEntity)
    }

    suspend fun getAllAlbumsData(userId: Int): List<AlbumsTuple> = albumsDao.getAllAlbumsData(userId)

    suspend fun removeAlbumsDataById(id: Int) {
        albumsDao.deleteAlbumsDataById(id)
    }
}