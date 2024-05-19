package com.yajhz.data.model.api

import com.google.gson.annotations.SerializedName

class DataWrapperModel<T> {
    var response_code = 0
    var success: Boolean = false
    var message: String? = null

    @SerializedName("data")
    var response: T? = null
        private set

}
