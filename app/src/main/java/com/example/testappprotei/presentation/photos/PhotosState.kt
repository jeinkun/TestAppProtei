package com.example.testappprotei.presentation.photos

import androidx.compose.runtime.mutableStateListOf

data class PhotosState (
    val photos: List<Photo?> = mutableStateListOf(null),
    var isLoading: Boolean = false
)

data class Photo (
    val id: Int,
    val title: String?,
    val url: String?
)