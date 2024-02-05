package com.example.testappprotei.dataBase.repo

import com.example.testappprotei.dataBase.dao.PhotosDao
import com.example.testappprotei.dataBase.model.PhotosEntity
import com.example.testappprotei.dataBase.model.PhotosTuple

class PhotosDbRepo (private val photosDao: PhotosDao) {

    suspend fun insertNewPhotosData(photosEntity: List<PhotosEntity>) {
        photosDao.insertNewPhotosData(photosEntity)
    }

    suspend fun getAllPhotosData(albumsId: Int): List<PhotosTuple> = photosDao.getAllPhotosData(albumsId)

    suspend fun removePhotosDataById(id: Int) {
        photosDao.deletePhotosDataById(id)
    }
}