package com.example.testappprotei.presentation.mappers

import com.example.testappprotei.dataBase.model.PhotosEntity
import com.example.testappprotei.dataBase.model.PhotosTuple
import com.example.testappprotei.network.model.PhotosModel
import com.example.testappprotei.presentation.photos.Photo

fun List<PhotosModel>.toPhotosState(): List<Photo?> = this.map { photo ->
    Photo(
        id = photo.id,
        title = photo.title,
        url = photo.url
    )
}

fun List<PhotosTuple>.toPhotosStateDb(): List<Photo> = this.map { photo ->
    Photo(
        id = photo.id,
        title = photo.title,
        url = photo.url
    )
}

fun List<PhotosModel>.toPhotosEntity(): List<PhotosEntity> = this.map { photo ->
    PhotosEntity(
        albumId = photo.albumId,
        id = photo.id,
        title = photo.title,
        url = photo.url,
        thumbnailUrl = photo.thumbnailUrl
    )
}