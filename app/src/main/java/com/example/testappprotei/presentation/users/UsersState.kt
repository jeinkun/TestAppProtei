package com.example.testappprotei.presentation.users

import androidx.compose.runtime.mutableStateListOf

data class UsersState(
    val users: List<User?> = mutableStateListOf(null),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class User(
    val id: Int,
    val name: String?,
    val email: String?,
    val phoneNumber: String?
)
