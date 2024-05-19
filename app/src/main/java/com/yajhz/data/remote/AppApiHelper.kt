package com.yajhz.data.remote

import com.yajhz.data.model.api.CategoryModel
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.data.model.api.LoginModel
import com.yajhz.data.model.api.SellerModel
import com.yajhz.data.model.api.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(private val networkService: NetworkService) : ApiHelper {


    override suspend fun doLoginApiCall(
        body: HashMap<String, String>
    ): DataWrapperModel<LoginModel> {
        return networkService.doLoginApiCall(body)
    }

    override suspend fun doRegistrationApiCall(
        body: HashMap<String, String>
    ): DataWrapperModel<LoginModel> {
        return networkService.doRegistrationApiCall(body)
    }

    override suspend fun getTrendingApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>> {
        return networkService.getTrendingApiCall(map)
    }

    override suspend fun getCategoriesApiCall(): DataWrapperModel<CategoryModel> {
        return networkService.getCategoriesApiCall()
    }

    override suspend fun getPopularApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>> {
        return networkService.getPopularApiCall(map)
    }

    override suspend fun doFavoriteApiCall(id: Int?): DataWrapperModel<Void> {
        return networkService.doFavoriteApiCall(id)
    }
}
