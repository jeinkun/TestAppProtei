package com.example.testappprotei.repository.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testappprotei.repository.dataBase.model.AlbumsEntity
import com.example.testappprotei.repository.dataBase.model.AlbumsTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumsDao {
    @Insert(entity = AlbumsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewAlbumsData(users: List<AlbumsEntity>)

    @Query("SELECT * FROM albums WHERE userId = :userId")
    fun getAllAlbumsData(userId: Int): Flow<List<AlbumsTuple>>

    @Query("SELECT COUNT(*) FROM albums")
    fun checkAlbumsData(): Int

    @Query("DELETE FROM albums WHERE id = :albumsId")
    suspend fun deleteAlbumsDataById(albumsId: Int)

    @Query("UPDATE albums SET favorite = :isFavorite WHERE id LIKE :idAlbum")
    suspend fun updateFavoriteAlbum(isFavorite: Boolean, idAlbum: Int) : Int
}