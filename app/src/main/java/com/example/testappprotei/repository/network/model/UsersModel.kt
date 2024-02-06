package com.example.testappprotei.repository.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    val id: Int,
    val name: String?,
    val userName: String?,
    val email: String?,
    val address: AddressModel?,
): Parcelable

@Parcelize
data class AddressModel (
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: GeoModel?,
    val phone: String?,
    val website: String?,
    val company: CompanyModel?
): Parcelable

@Parcelize
data class GeoModel(
    val lat: String?,
    val lng: String?
): Parcelable

@Parcelize
data class CompanyModel(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
): Parcelable