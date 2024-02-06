package com.example.testappprotei.repository.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import com.example.testappprotei.repository.dataBase.model.PhotosTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Insert(entity = PhotosEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPhotosData(users: List<PhotosEntity>)

    @Query("SELECT * FROM photos WHERE albumId = :albumId")
    fun getAllPhotosData(albumId: Int): Flow<List<PhotosTuple>>

    @Query("SELECT COUNT(*) FROM photos")
    fun checkPhotosData(): Int


    @Query("DELETE FROM photos WHERE id = :photoId")
    suspend fun deletePhotosDataById(photoId: Int) : Int
}