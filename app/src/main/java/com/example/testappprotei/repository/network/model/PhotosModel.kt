package com.example.testappprotei.repository.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosModel(
    val albumId: Int,
    val id: Int,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
) : Parcelable
