package com.example.testappprotei.repository.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testappprotei.repository.dataBase.model.UsersEntity
import com.example.testappprotei.repository.dataBase.model.UsersTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(entity = UsersEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUsersData(users: List<UsersEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsersData(): Flow<List<UsersTuple>>

    @Query("SELECT COUNT(*) FROM users")
    fun checkUsersData(): Int

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUsersDataById(userId: Int)
}