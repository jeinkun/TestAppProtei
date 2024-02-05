package com.example.testappprotei.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.dataBase.model.AlbumsTuple

@Dao
interface AlbumsDao {
    @Insert(entity = AlbumsEntity::class)
    fun insertNewAlbumsData(users: List<AlbumsEntity>)

    @Query("SELECT * FROM albums WHERE userId = :userId")
    fun getAllAlbumsData(userId: Int): List<AlbumsTuple>

    @Query("DELETE FROM albums WHERE id = :albumsId")
    fun deleteAlbumsDataById(albumsId: Int)
}