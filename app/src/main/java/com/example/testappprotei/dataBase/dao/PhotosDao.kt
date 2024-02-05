package com.example.testappprotei.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testappprotei.dataBase.model.PhotosEntity
import com.example.testappprotei.dataBase.model.PhotosTuple

@Dao
interface PhotosDao {
    @Insert(entity = PhotosEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewPhotosData(users: List<PhotosEntity>)

    @Query("SELECT * FROM photos WHERE albumId = :albumId")
    fun getAllPhotosData(albumId: Int): List<PhotosTuple>

    @Query("DELETE FROM photos WHERE id = :photoId")
    fun deletePhotosDataById(photoId: Int) : Int
}