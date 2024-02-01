package com.example.testappprotei.presentation.users

import androidx.compose.runtime.mutableStateListOf
import com.example.testappprotei.network.model.UserModel

data class UsersState (
    val users: List<User?> = mutableStateListOf(null)
)

data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phoneNumber: String?
)

fun List<UserModel>.toUsersState() : List<User?> {
    val usersState: MutableList<User?> = mutableStateListOf(null)
    this.forEach { user ->
        usersState.add(
            User(
                id = user.id,
                name = user.name,
                email = user.email,
                phoneNumber = user.address.phone
            )
        )
    }
    return usersState.toList()
}