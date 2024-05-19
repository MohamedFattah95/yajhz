package com.yajhz.data.local.prefs

import com.yajhz.data.DataManager
import com.yajhz.data.model.api.UserModel

interface PreferencesHelper {
    fun isUserLogged(): Boolean

    fun setUserObject(userModel: UserModel?)

    fun getUserObject(): UserModel?

    fun saveLanguage(lang: String)

    fun getLanguage(): String?

    fun setFirstTimeLaunch(isFirstTime: Boolean)

    fun isFirstTimeLaunch(): Boolean

    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)

    fun getCurrentUserEmail(): String?

    fun setCurrentUserEmail(email: String?)

    fun getCurrentUserId(): String?

    fun setCurrentUserId(userId: String?)

    fun getCurrentUserLoggedInMode(): Int

    fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode?)

    fun getCurrentUserName(): String?

    fun setCurrentUserName(userName: String?)

    fun getCurrentUserProfilePicUrl(): String?

    fun setCurrentUserProfilePicUrl(profilePicUrl: String?)

    fun isDarkMode(): Boolean

    fun setIsDarkMode(isDark: Boolean)
    fun setNoDisturb(flag: Int)

    fun getNoDisturb(): Int

}
