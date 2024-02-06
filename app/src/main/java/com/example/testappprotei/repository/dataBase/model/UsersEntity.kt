package com.example.testappprotei.repository.dataBase.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testappprotei.repository.network.model.AddressModel

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val userName: String?,
    val email: String?,
    @Embedded
    val address: AddressEntity?,
)

data class AddressEntity (
    val street: String?,
    val suit: String?,
    val city: String?,
    val zipcode: String?,
    @Embedded
    val geo: GeoEntity?,
    val phone: String?,
    val website: String?,
    @Embedded
    val company: CompanyEntity?
)

data class GeoEntity(
    val lat: String?,
    val lng: String?
)

data class CompanyEntity(
    val companyName: String?,
    val catchPhrase: String?,
    val bs: String?
)