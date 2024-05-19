package com.yajhz.data

import android.content.Context
import com.google.gson.Gson
import com.yajhz.data.local.prefs.PreferencesHelper
import com.yajhz.data.model.api.CategoryModel
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.data.model.api.LoginModel
import com.yajhz.data.model.api.SellerModel
import com.yajhz.data.model.api.UserModel
import com.yajhz.data.remote.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager :
    DataManager {
    val mContext: Context

    @Inject
    constructor(
        mContext: Context,
        preferencesHelper: PreferencesHelper,
        apiHelper: ApiHelper,
        gson: Gson
    ) {
        this.mContext = mContext
        mPreferencesHelper = preferencesHelper
        mApiHelper = apiHelper
        mGson = gson
    }

    private val mApiHelper: ApiHelper
    private val mGson: Gson
    private val mPreferencesHelper: PreferencesHelper


    override fun isUserLogged(): Boolean {
        return mPreferencesHelper.isUserLogged()
    }

    override fun saveLanguage(lang: String) {
        mPreferencesHelper.saveLanguage(lang)
    }

    override fun getLanguage(): String? {
        return mPreferencesHelper.getLanguage()
    }

    override fun setFirstTimeLaunch(isFirstTime: Boolean) {
        mPreferencesHelper.setFirstTimeLaunch(isFirstTime)
    }

    override fun isFirstTimeLaunch(): Boolean {
        return mPreferencesHelper.isFirstTimeLaunch()
    }

    override fun getAccessToken(): String? {
        return mPreferencesHelper.getAccessToken()
    }

    override fun setAccessToken(accessToken: String?) {
        mPreferencesHelper.setAccessToken(accessToken)
    }

    override fun getCurrentUserEmail(): String? {
        return mPreferencesHelper.getCurrentUserEmail()
    }

    override fun setCurrentUserEmail(email: String?) {
        mPreferencesHelper.setCurrentUserEmail(email)
    }

    override fun getCurrentUserId(): String? {
        return mPreferencesHelper.getCurrentUserId()
    }

    override fun setCurrentUserId(userId: String?) {
        mPreferencesHelper.setCurrentUserId(userId)
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.getCurrentUserLoggedInMode()
    }

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode?) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserName(): String? {
        return mPreferencesHelper.getCurrentUserName()
    }

    override fun setCurrentUserName(userName: String?) {
        mPreferencesHelper.setCurrentUserName(userName)
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return mPreferencesHelper.getCurrentUserProfilePicUrl()
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl)
    }

    override fun isDarkMode(): Boolean {
        return mPreferencesHelper.isDarkMode()
    }

    override fun setIsDarkMode(isDark: Boolean) {
        mPreferencesHelper.setIsDarkMode(isDark)
    }

    override fun setNoDisturb(flag: Int) {
        mPreferencesHelper.setNoDisturb(flag)
    }

    override fun getNoDisturb(): Int {
        return mPreferencesHelper.getNoDisturb()
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(null, DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)
    }

    override fun setUserObject(userModel: UserModel?) {
        mPreferencesHelper.setUserObject(userModel)
    }

    override fun getUserObject(): UserModel? {
        return mPreferencesHelper.getUserObject()
    }

    override fun updateUserInfo(user: LoginModel?, loggedInMode: DataManager.LoggedInMode?) {
        setCurrentUserLoggedInMode(loggedInMode)

        if (user != null) {
            setAccessToken(user.token)
            setCurrentUserId(user.id.toString())
            setCurrentUserEmail(user.email)
            setCurrentUserName(user.name)
            setCurrentUserProfilePicUrl(user.image)
        } else {
            setAccessToken(null)
            setCurrentUserId(null)
            setCurrentUserEmail(null)
            setCurrentUserName(null)
            setCurrentUserProfilePicUrl(null)
        }
    }

    override suspend fun doLoginApiCall(
        body: HashMap<String, String>,
    ): DataWrapperModel<LoginModel> {
        return mApiHelper.doLoginApiCall(body)
    }

    override suspend fun doRegistrationApiCall(
        body: HashMap<String, String>
    ): DataWrapperModel<LoginModel> {
        return mApiHelper.doRegistrationApiCall(body)
    }

    override suspend fun getCategoriesApiCall(): DataWrapperModel<CategoryModel> {
        return mApiHelper.getCategoriesApiCall()
    }

    override suspend fun getTrendingApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>> {
        return mApiHelper.getTrendingApiCall(map)
    }

    override suspend fun getPopularApiCall(map: HashMap<String, Any>): DataWrapperModel<List<SellerModel>> {
        return mApiHelper.getPopularApiCall(map)
    }

    override suspend fun doFavoriteApiCall(id: Int?): DataWrapperModel<Void> {
        return mApiHelper.doFavoriteApiCall(id)
    }

    companion object {
        private const val TAG = "AppDataManager"
    }

}
