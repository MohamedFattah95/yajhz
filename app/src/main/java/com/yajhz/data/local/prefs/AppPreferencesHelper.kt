package com.yajhz.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yajhz.MvvmApp
import com.yajhz.data.DataManager
import com.yajhz.data.model.api.UserModel
import com.yajhz.di.PreferenceInfo
import com.yajhz.utils.AppConstants
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo prefFileName: String
) : PreferencesHelper {

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun isUserLogged(): Boolean {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null) != null
    }

    override fun saveLanguage(lang: String) {
        val editor = mPrefs.edit()
        editor.putString(LANGUAGE, lang)
        editor.apply()
    }

    override fun getLanguage(): String? {
        return mPrefs.getString(LANGUAGE, "en")
    }

    override fun setFirstTimeLaunch(isFirstTime: Boolean) {
        val editor = mPrefs.edit()
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.apply()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return mPrefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    override fun getAccessToken(): String? {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    override fun setAccessToken(accessToken: String?) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun getCurrentUserEmail(): String? {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null)
    }

    override fun setCurrentUserEmail(email: String?) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email)
            .apply()
    }

    override fun getCurrentUserId(): String? {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ID, null)
    }

    override fun setCurrentUserId(userId: String?) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply()
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPrefs.getInt(
            PREF_KEY_USER_LOGGED_IN_MODE,
            DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type
        )
    }

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode?) {
        mode?.type?.let {
            mPrefs.edit()
                .putInt(PREF_KEY_USER_LOGGED_IN_MODE, it)
                .apply()
        }
    }

    override fun getCurrentUserName(): String? {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null)
    }

    override fun setCurrentUserName(userName: String?) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName)
            .apply()
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return mPrefs.getString(
            PREF_KEY_CURRENT_USER_PROFILE_PIC_URL,
            ""
        )
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String?) {
        mPrefs.edit().putString(
            PREF_KEY_CURRENT_USER_PROFILE_PIC_URL,
            profilePicUrl
        ).apply()
    }

    override fun isDarkMode(): Boolean {
        return mPrefs.getBoolean(IS_DARK_MODE, false)
    }

    override fun setIsDarkMode(isDark: Boolean) {
        mPrefs.edit().putBoolean(IS_DARK_MODE, isDark).apply()
    }

    override fun setNoDisturb(flag: Int) {
        mPrefs.edit().putInt(PREF_KEY_NO_DISTURB, flag).apply()
    }

    override fun getNoDisturb(): Int {
        return mPrefs.getInt(PREF_KEY_NO_DISTURB, 0)
    }

    override fun getUserObject(): UserModel? {
        val objStr = mPrefs.getString(USER_OBJECT, "")
        return if (objStr?.isNotEmpty()!!) {
            Gson().fromJson(objStr, UserModel::class.java)
        } else null
    }

    override fun setUserObject(userModel: UserModel?) {
        mPrefs.edit().putString(USER_OBJECT, Gson().toJson(userModel)).apply()
    }

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL =
            "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
        private const val USER_OBJECT = "PREF_USER_OBJECT"
        private const val LANGUAGE = "PREF_LANGUAGE"
        private const val IS_FIRST_TIME_LAUNCH = "PREF_FIRST_TIME"
        private const val IS_DARK_MODE = "IS_DARK_MODE"
        private const val PREF_KEY_NO_DISTURB = "PREF_KEY_NO_DISTURB"
        private var mSharedPrefs: AppPreferencesHelper? = null
        val instance: AppPreferencesHelper?
            get() {
                if (mSharedPrefs == null) {
                    mSharedPrefs =
                        AppPreferencesHelper(MvvmApp.context!!, AppConstants.PREF_NAME)
                }
                return mSharedPrefs
            }
    }

}
