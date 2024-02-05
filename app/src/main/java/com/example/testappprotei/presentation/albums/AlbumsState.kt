package com.example.testappprotei.presentation.albums

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.network.model.AlbumsModel

data class AlbumsState(
    val albums: List<Album?> = mutableStateListOf(null),
    var isLoading: Boolean = false
)

data class Album(
    val id: Int,
    val title: String?,
    val favorite: Boolean = false
)
