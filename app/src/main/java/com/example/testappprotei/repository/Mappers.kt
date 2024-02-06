package com.example.testappprotei.repository

import com.example.testappprotei.presentation.mappers.toAddressEntity
import com.example.testappprotei.repository.dataBase.model.AddressEntity
import com.example.testappprotei.repository.dataBase.model.AlbumsEntity
import com.example.testappprotei.repository.dataBase.model.CompanyEntity
import com.example.testappprotei.repository.dataBase.model.GeoEntity
import com.example.testappprotei.repository.dataBase.model.PhotosEntity
import com.example.testappprotei.repository.dataBase.model.UsersEntity
import com.example.testappprotei.repository.dataBase.model.UsersTuple
import com.example.testappprotei.repository.network.model.AddressModel
import com.example.testappprotei.repository.network.model.AlbumsModel
import com.example.testappprotei.repository.network.model.CompanyModel
import com.example.testappprotei.repository.network.model.GeoModel
import com.example.testappprotei.repository.network.model.PhotosModel
import com.example.testappprotei.repository.network.model.UserModel


fun List<UserModel>.toUserData(): List<UsersTuple> = this.map { user ->
    UsersTuple(
        id = user.id,
        name = user.name,
        userName = user.userName,
        email = user.email,
        address = user.address?.addressDataMap()
    )
}

fun AddressModel.addressDataMap(): AddressEntity = AddressEntity(
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

fun List<UserModel>.toUsersEntity(): List<UsersEntity> = this.map { user ->
    UsersEntity(
        id = user.id,
        name = user.name,
        userName = user.userName,
        email = user.email,
        address = user.address?.toAddressEntity(),
    )
}

fun List<PhotosModel>.toPhotosEntity(): List<PhotosEntity> = this.map { photo ->
    PhotosEntity(
        albumId = photo.albumId,
        id = photo.id,
        title = photo.title,
        url = photo.url,
        thumbnailUrl = photo.thumbnailUrl
    )
}

fun List<AlbumsModel>.toAlbumsEntity(): List<AlbumsEntity> = this.map { album ->
    AlbumsEntity(
        userId = album.userId,
        id = album.id,
        title = album.title,
        favorite = false
    )
}
