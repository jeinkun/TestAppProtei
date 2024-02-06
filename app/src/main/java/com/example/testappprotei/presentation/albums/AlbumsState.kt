package com.example.testappprotei.presentation.albums

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.repository.network.model.AlbumsModel

data class AlbumsState(
    val albums: List<Album?> = mutableStateListOf(null),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class Album(
    val id: Int,
    val userId: Int,
    val title: String?,
    val favorite: Boolean = false
)


//sealed class State<T>() {
//    object Loading : State<T>()
//
//    data class Error <T> (val message: String): State<T>()
//
//    data class Success <T> (val data : T): State<T>()
//}