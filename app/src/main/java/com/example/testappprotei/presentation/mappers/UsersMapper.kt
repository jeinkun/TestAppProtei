package com.example.testappprotei.presentation.mappers

import com.example.testappprotei.repository.dataBase.model.AddressEntity
import com.example.testappprotei.repository.dataBase.model.CompanyEntity
import com.example.testappprotei.repository.dataBase.model.GeoEntity
import com.example.testappprotei.repository.dataBase.model.UsersEntity
import com.example.testappprotei.repository.dataBase.model.UsersTuple
import com.example.testappprotei.repository.network.model.AddressModel
import com.example.testappprotei.repository.network.model.CompanyModel
import com.example.testappprotei.repository.network.model.GeoModel
import com.example.testappprotei.repository.network.model.UserModel
import com.example.testappprotei.presentation.users.User


fun List<UserModel>.toUsersState(): List<User> = this.map { user ->
    User(
        id = user.id,
        name = user.name,
        email = user.email,
        phoneNumber = user.address?.phone
    )
}

fun List<UsersTuple>.toUsersStateDb(): List<User> = this.map { user ->
    User(
        id = user.id,
        name = user.name,
        email = user.email,
        phoneNumber = user.address?.phone
    )
}

fun List<UserModel>.toUsersEntity(): List<UsersEntity> = this.map { user ->
    UsersEntity(
        id = user.id,
        name = user.name,
        userName = user.userName,
        email = user.email,
        address = user.address?.toAddressEntity(),
    )
}

fun AddressModel.toAddressEntity(): AddressEntity = AddressEntity(
    street = this.street,
    suit = this.suite,
    city = this.city,
    zipcode = this.zipcode,
    geo = this.geo?.toGeoEntity(),
    phone = this.phone,
    website = this.website,
    company = this.company?.toCompanyEntity()
)

fun GeoModel.toGeoEntity(): GeoEntity = GeoEntity(
    lat = this.lat,
    lng = this.lng
)

fun CompanyModel.toCompanyEntity(): CompanyEntity = CompanyEntity(
    companyName = this.name,
    catchPhrase = this.catchPhrase,
    bs = this.bs
)