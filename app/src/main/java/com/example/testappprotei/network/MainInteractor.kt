package com.example.testappprotei.network

class MainInteractor() {
    private val apiRepository = ApiRepository()

    suspend fun getUsers() = apiRepository.getUsers()

    suspend fun getUsersAlbums(userId: Int) = apiRepository.getUserAlbums(userId)
}