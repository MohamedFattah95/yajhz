package com.yajhz.data.model.api


import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("file_name")
    var fileName: String,
    @SerializedName("id")
    var id: Int
)