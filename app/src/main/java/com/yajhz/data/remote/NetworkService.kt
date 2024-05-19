package com.yajhz.data.remote

import com.yajhz.data.model.api.*
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface NetworkService {

    @POST("login")
    suspend fun doLoginApiCall(@Body body: HashMap<String, String>): DataWrapperModel<LoginModel>

    @FormUrlEncoded
    @POST("logout")
    suspend fun doLogout(@Field("user_id") userId: String?): DataWrapperModel<Void>

    @POST("client-register")
    suspend fun doRegistrationApiCall(@Body body: HashMap<String, String>): DataWrapperModel<LoginModel>

    @GET("client-profile")
    suspend fun getProfileApiCall(): DataWrapperModel<UserModel>

    @GET("home-base-categories")
    suspend fun getCategoriesApiCall(): DataWrapperModel<CategoryModel>

    @GET("trending-sellers")
    suspend fun getTrendingApiCall(@QueryMap map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>>

    @GET("popular-sellers")
    suspend fun getPopularApiCall(@QueryMap map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>>

    @FormUrlEncoded
    @POST("favorite")
    suspend fun doFavoriteApiCall(@Field("user_id") id: Int?): DataWrapperModel<Void>

}