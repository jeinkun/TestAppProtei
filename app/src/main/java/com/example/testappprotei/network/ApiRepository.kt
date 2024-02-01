package com.example.testappprotei.network

class ApiRepository() {
    private val retrofit = RetrofitHelper

    suspend fun getUsers() = retrofit.apiService.getUsers()

    suspend fun getUserAlbums(userId: Int) = retrofit.apiService.getUserAlbums(userId)
}