package com.yajhz.data

import com.yajhz.data.local.prefs.PreferencesHelper
import com.yajhz.data.model.api.LoginModel
import com.yajhz.data.remote.ApiHelper

interface DataManager : PreferencesHelper, ApiHelper {

    fun setUserAsLoggedOut()
    fun updateUserInfo(user: LoginModel?, loggedInMode: LoggedInMode?)

    enum class LoggedInMode(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

    }
}
