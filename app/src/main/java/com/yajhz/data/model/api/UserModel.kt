package com.yajhz.data.model.api


import java.io.Serializable

data class UserModel(
    val birthday: String?,
    val channelsIds: List<String?>?,
    val checkInCount: Int?,
    val checkedInPlaces: Int?,
    val createdOn: String?,
    val email: String?,
    val firstName: String?,
    val frindsCount: Int?,
    val gender: Boolean?,
    val id: String?,
    var imageUrl: String?,
    var isFollowing: Boolean = false,
    val isGolden: Boolean?,
    val lastName: String?,
    val mesha3lilPoints: Int?,
    val mobileNumber: String?,
    val userName: String?,
    val userLatitude: Double?,
    val userLongitude: Double?
) : Serializable