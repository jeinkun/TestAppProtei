package com.example.testappprotei.presentation.mappers

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.dataBase.model.AlbumsTuple
import com.example.testappprotei.network.model.AlbumsModel
import com.example.testappprotei.presentation.albums.Album


fun List<AlbumsModel>.toAlbumsState(): List<Album?> = this.map { album ->
    Album(id = album.id, userId = album.userId, title = album.title)
}

fun List<AlbumsTuple>.toAlbumsStateDb(): List<Album> = this.map { album ->
    Album(
        id = album.id,
        userId = album.id,
        title = album.title,
        favorite = album.favorite
    )
}

fun List<AlbumsModel>.toAlbumsEntity(): List<AlbumsEntity> = this.map { album ->
    AlbumsEntity(
        userId = album.userId,
        id = album.id,
        title = album.title,
        favorite = false
    )
}

fun Album.toAlbumEntity(): AlbumsEntity =
    AlbumsEntity(
        userId = this.userId,
        id = this.id,
        title = this.title,
        favorite = this.favorite
    )
