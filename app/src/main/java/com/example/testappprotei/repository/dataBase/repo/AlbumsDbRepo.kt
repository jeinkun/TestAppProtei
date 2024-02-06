package com.example.testappprotei.repository.dataBase.repo

import com.example.testappprotei.repository.dataBase.dao.AlbumsDao
import com.example.testappprotei.repository.dataBase.model.AlbumsEntity
import com.example.testappprotei.repository.dataBase.model.AlbumsTuple
import com.example.testappprotei.repository.network.ApiRepository
import com.example.testappprotei.repository.toAlbumsEntity
import com.example.testappprotei.repository.toPhotosEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AlbumsDbRepo(private val albumsDao: AlbumsDao) {
    private val apiRepository = ApiRepository()

    suspend fun insertNewAlbumsData(albumsEntity: List<AlbumsEntity>) {
        withContext(Dispatchers.IO) { albumsDao.insertNewAlbumsData(albumsEntity) }
    }

    suspend fun getAllAlbumsData(userId: Int): Flow<List<AlbumsTuple>> =
        withContext(Dispatchers.IO) {
            val countData = albumsDao.checkAlbumsData()
            if (countData == 0) {
                val result = apiRepository.getUserAlbums(userId).body()
                result?.toAlbumsEntity()?.let { albumsDao.insertNewAlbumsData(it) }
            }
            return@withContext albumsDao.getAllAlbumsData(userId)
        }

    suspend fun removeAlbumsDataById(id: Int) {
        withContext(Dispatchers.IO) { albumsDao.deleteAlbumsDataById(id) }
    }

    suspend fun updateFavoriteAlbum(isFavorite: Boolean, idAlbum: Int): Int {
        return withContext(Dispatchers.IO) { albumsDao.updateFavoriteAlbum(isFavorite, idAlbum) }
    }
}