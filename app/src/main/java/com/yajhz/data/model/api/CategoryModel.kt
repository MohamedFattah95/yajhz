package com.yajhz.data.model.api

data class CategoryModel(
    val cart_count: Int?,
    val `data`: List<Data>?
) {

    data class Data(
        val active: Int?,
        val id: Int?,
        val image: String?,
        val name: String?,
        val name_ar: String?,
        val name_en: String?
    )
}
