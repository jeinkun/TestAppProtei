package com.example.testappprotei.repository.network

class ApiRepository() {
    private val retrofit = RetrofitHelper

    suspend fun getUsers() = RetrofitHelper.apiService.getUsers()

    suspend fun getUserAlbums(userId: Int) = RetrofitHelper.apiService.getUserAlbums(userId)

    suspend fun getAlbumsPhoto(albumId: Int) = RetrofitHelper.apiService.getAlbumsPhoto(albumId)
}