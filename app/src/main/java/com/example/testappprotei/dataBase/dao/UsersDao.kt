package com.example.testappprotei.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testappprotei.dataBase.model.UsersEntity
import com.example.testappprotei.dataBase.model.UsersTuple

@Dao
interface UsersDao {

    @Insert(entity = UsersEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertNewUsersData(users: List<UsersEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsersData(): List<UsersTuple>

    @Query("DELETE FROM users WHERE id = :userId")
    fun deleteUsersDataById(userId: Int)
}