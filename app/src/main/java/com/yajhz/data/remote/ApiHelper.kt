package com.yajhz.data.remote

import com.yajhz.data.model.api.CategoryModel
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.data.model.api.LoginModel
import com.yajhz.data.model.api.SellerModel
import com.yajhz.data.model.api.UserModel

interface ApiHelper {

    suspend fun doLoginApiCall(
        body: HashMap<String, String>,
    ): DataWrapperModel<LoginModel>


    suspend fun doRegistrationApiCall(body: HashMap<String, String>): DataWrapperModel<LoginModel>
    suspend fun getCategoriesApiCall() : DataWrapperModel<CategoryModel>
    suspend fun getTrendingApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>>
    suspend fun getPopularApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>>
    suspend fun doFavoriteApiCall(id: Int?): DataWrapperModel<Void>


}