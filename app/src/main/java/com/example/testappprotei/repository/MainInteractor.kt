package com.example.testappprotei.repository

import com.example.testappprotei.repository.dataBase.Dependencies
import com.example.testappprotei.repository.network.ApiRepository

class MainInteractor() {
    private val apiRepository = ApiRepository()

    suspend fun getUsers() = apiRepository.getUsers()

    suspend fun getUsersAlbums(userId: Int) = apiRepository.getUserAlbums(userId)

    suspend fun getAlbumsPhoto(albumId: Int) = apiRepository.getAlbumsPhoto(albumId)
}