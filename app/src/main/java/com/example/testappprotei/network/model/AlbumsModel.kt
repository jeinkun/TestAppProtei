package com.example.testappprotei.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumsModel(
    val userId: Int,
    val id: Int,
    val title: String
) : Parcelable
