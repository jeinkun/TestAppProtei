package com.example.testappprotei.presentation.users

import androidx.compose.runtime.mutableStateListOf
import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.example.testappprotei.dataBase.model.AddressEntity
import com.example.testappprotei.dataBase.model.UsersEntity
import com.example.testappprotei.network.model.UserModel

data class UsersState(
    val users: List<User?> = mutableStateListOf(null),
    var isLoading: Boolean = false
)

data class User(
    val id: Int,
    val name: String?,
    val email: String?,
    val phoneNumber: String?
)
