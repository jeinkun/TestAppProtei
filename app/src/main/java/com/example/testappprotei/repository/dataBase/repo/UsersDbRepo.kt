package com.example.testappprotei.repository.dataBase.repo

import com.example.testappprotei.repository.dataBase.dao.UsersDao
import com.example.testappprotei.repository.dataBase.model.UsersEntity
import com.example.testappprotei.repository.dataBase.model.UsersTuple
import com.example.testappprotei.repository.network.ApiRepository
import com.example.testappprotei.repository.toUsersEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UsersDbRepo(private val usersDao: UsersDao) {

    private val apiRepository = ApiRepository()

    suspend fun insertNewUsersData(usersEntity: List<UsersEntity>) {
        withContext(Dispatchers.IO) { usersDao.insertNewUsersData(usersEntity) }
    }

    suspend fun getAllUsersData(): Flow<List<UsersTuple>> =
        withContext(Dispatchers.IO) {
            val countData = usersDao.checkUsersData()
            if (countData == 0) {
                val result = apiRepository.getUsers().body()
                result?.toUsersEntity()?.let { usersDao.insertNewUsersData(it) }
            }
            return@withContext usersDao.getAllUsersData()
        }


    suspend fun removeUsersDataById(id: Int) {
        withContext(Dispatchers.IO) { usersDao.deleteUsersDataById(id) }
    }
}
