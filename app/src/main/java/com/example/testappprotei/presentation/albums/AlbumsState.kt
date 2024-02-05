package com.example.testappprotei.presentation.albums

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.network.model.AlbumsModel

data class AlbumsState(
    val albums: List<Album?> = mutableStateListOf(null),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class Album(
    val id: Int,
    val userId: Int,
    val title: String?,
    var favorite: Boolean = false
)
