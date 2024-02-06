package com.example.testappprotei.repository.dataBase.repo

import com.example.testappprotei.repository.dataBase.dao.AlbumsDao
import com.example.testappprotei.repository.dataBase.dao.PhotosDao
import com.example.testappprotei.repository.dataBase.dao.UsersDao
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import com.example.testappprotei.repository.dataBase.model.PhotosTuple
import com.example.testappprotei.repository.network.ApiRepository
import com.example.testappprotei.repository.toPhotosEntity
import com.example.testappprotei.repository.toUsersEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PhotosDbRepo(private val photosDao: PhotosDao) {
    private val apiRepository = ApiRepository()

    suspend fun insertNewPhotosData(photosEntity: List<PhotosEntity>) {
        withContext(Dispatchers.IO) { photosDao.insertNewPhotosData(photosEntity) }
    }

    suspend fun getAllPhotosData(albumsId: Int): Flow<List<PhotosTuple>> =
        withContext(Dispatchers.IO) {
            val countData = photosDao.checkPhotosData()
            if (countData == 0) {
                val result = apiRepository.getAlbumsPhoto(albumsId)
                result.body()?.toPhotosEntity()?.let { photosDao.insertNewPhotosData(it) }
            }
            return@withContext photosDao.getAllPhotosData(albumsId)
        }


    suspend fun removePhotosDataById(id: Int): Int =
        withContext(Dispatchers.IO) { photosDao.deletePhotosDataById(id) }
}