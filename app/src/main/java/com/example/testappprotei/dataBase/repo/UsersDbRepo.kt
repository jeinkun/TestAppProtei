package com.example.testappprotei.dataBase.repo

import com.example.testappprotei.dataBase.dao.UsersDao
import com.example.testappprotei.dataBase.model.UsersEntity
import com.example.testappprotei.dataBase.model.UsersTuple

class UsersDbRepo(private val usersDao: UsersDao)  {

    suspend fun insertNewUsersData(usersEntity: List<UsersEntity>) {
        usersDao.insertNewUsersData(usersEntity)
    }

    suspend fun getAllUsersData(): List<UsersTuple> = usersDao.getAllUsersData()

    suspend fun removeUsersDataById(id: Int) {
        usersDao.deleteUsersDataById(id)
    }
}