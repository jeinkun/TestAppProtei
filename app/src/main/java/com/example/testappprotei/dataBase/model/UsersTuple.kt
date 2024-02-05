package com.example.testappprotei.dataBase.model

import androidx.room.Embedded

data class UsersTuple(
    val id: Int,
    val name: String?,
    val userName: String?,
    val email: String?,
    @Embedded
    val address: AddressEntity?,
)
