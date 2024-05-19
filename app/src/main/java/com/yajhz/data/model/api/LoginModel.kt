package com.yajhz.data.model.api


import java.io.Serializable

data class LoginModel(
    val addresses: List<Addresse?>?,
    val balance: String?,
    val email: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val phone: String?,
    val status: Int?,
    val token: String?,
    val type: Int?
) : Serializable {

    data class Addresse(
        val address: Any?,
        val apartment: String?,
        val building: String?,
        val floor: Any?,
        val id: Int?,
        val lat: String?,
        val lng: String?,
        val name: Any?,
        val street: String?
    )
}