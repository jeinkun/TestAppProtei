package com.example.testappprotei.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testappprotei.dataBase.model.AlbumsEntity
import com.example.testappprotei.dataBase.model.AlbumsTuple

@Dao
interface AlbumsDao {
    @Insert(entity = AlbumsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewAlbumsData(users: List<AlbumsEntity>)

    @Query("SELECT * FROM albums WHERE userId = :userId")
    fun getAllAlbumsData(userId: Int): List<AlbumsTuple>

    @Query("DELETE FROM albums WHERE id = :albumsId")
    fun deleteAlbumsDataById(albumsId: Int)

    @Query("UPDATE albums SET favorite = :isFavorite WHERE id LIKE :idAlbum")
    fun updateFavoriteAlbum(isFavorite: Boolean, idAlbum: Int) : Int
}