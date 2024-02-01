package com.example.testappprotei.presentation.albums

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.network.model.AlbumsModel
import com.example.testappprotei.network.model.UserModel
import com.example.testappprotei.presentation.users.User
import com.example.testappprotei.presentation.users.toUsersState

data class AlbumsState(
    val albums: List<Album?> = mutableStateListOf(null)
)

data class Album(
    val id: Int?,
    val title: String?,
)

fun List<AlbumsModel>.toAlbumsState() : List<Album?> {
    val albumsState: MutableList<Album?> = mutableStateListOf(null)
    this.forEach { album ->
        albumsState.add(
            Album(id = album.id, title = album.title)
        )
    }
    return albumsState.toList()
}