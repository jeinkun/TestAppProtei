package com.example.testappprotei.dataBase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = AlbumsEntity::class,
            parentColumns = ["id"],
            childColumns = ["albumId"]
        )
    ]
)
data class PhotosEntity(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)