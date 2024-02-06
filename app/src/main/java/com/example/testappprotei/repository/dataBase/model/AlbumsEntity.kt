package com.example.testappprotei.repository.dataBase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = UsersEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete=CASCADE
        )
    ]
)
data class AlbumsEntity(
    val userId: Int,
    @PrimaryKey
    val id: Int,
    val title: String?,
    val favorite: Boolean
)