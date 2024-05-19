package com.yajhz.data.model.api

data class SellerModel(
    val address: String?,
    val appointments: String?,
    val categories: List<Category?>?,
    val description: String?,
    val distance: String?,
    val email: String?,
    val id: Int?,
    val image: String?,
    val information: Information?,
    var is_favorite: Boolean = false,
    val lat: String?,
    val lng: String?,
    val logo: String?,
    val name: String?,
    val phone: String?,
    val popular: Int?,
    val product_categories: List<ProductCategory?>?,
    val rate: String?,
    val status: Int?,
    val token: String?,
    val trending: Int?,
    val type: Int?
) {

    data class Category(
        val active: Int?,
        val id: Int?,
        val image: String?,
        val name: String?
    )

    data class Information(
        val activity: Any?,
        val driving_image: String?,
        val id: Int?,
        val identity_number: String?,
        val nationality: String?,
        val tax_record: String?,
        val vehicle_image: String?,
        val vehicle_registration: String?,
        val vehicle_tablet_image: String?
    )

    data class ProductCategory(
        val active: Int?,
        val id: Int?,
        val name: String?,
        val name_ar: String?,
        val name_en: String?
    )
}