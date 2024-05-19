package com.yajhz.data.model.api

import com.google.gson.annotations.SerializedName

class PagDataWrapperModel<T> {
    var code = 0
    var status: String? = null
    var message: String? = null

    @SerializedName("data")
    var data: T? = null
        private set

    @SerializedName("paginate")
    var pagination: Pagination? = null

    fun setData(data: T) {
        this.data = data
    }

    class Pagination {
        var total = 0
        var lastPage = 0
        var currentPage = 0

    }
}
