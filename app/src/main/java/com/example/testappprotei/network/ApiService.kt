package com.example.testappprotei.network

import com.example.testappprotei.network.model.AlbumsModel
import com.example.testappprotei.network.model.PhotosModel
import com.example.testappprotei.network.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers() : Response<List<UserModel>>

    @GET("albums")
    suspend fun getUserAlbums(@Query("userId") userId: Int) : Response<List<AlbumsModel>>

    @GET("photos")
    suspend fun getAlbumsPhoto(@Query("albumId") albumId: Int) : Response<List<PhotosModel>>
}