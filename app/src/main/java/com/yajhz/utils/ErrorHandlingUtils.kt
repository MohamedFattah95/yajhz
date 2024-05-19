package com.yajhz.utils

import android.content.Intent
import android.widget.Toast
import com.yajhz.MvvmApp.Companion.context
import com.yajhz.R
import com.yajhz.data.DataManager
import com.yajhz.data.local.prefs.AppPreferencesHelper
import com.yajhz.ui.user.login.LoginActivity
import es.dmoral.toasty.Toasty
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorHandlingUtils {

    val appPreferencesHelper: AppPreferencesHelper = AppPreferencesHelper.instance!!


    fun handleErrors(e: Throwable) {

        e.printStackTrace()

        if (e is UnknownHostException) {
            Toasty.error(
                context!!,
                context!!.resources.getString(R.string.check_internet_conn),
                Toast.LENGTH_LONG,
                true
            ).show()
        } else if (e is SocketTimeoutException) {
            Toasty.error(
                context!!,
                context!!.resources.getString(R.string.weak_internet_conn),
                Toast.LENGTH_LONG,
                true
            ).show()
        } else if (e is HttpException && e.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            if (appPreferencesHelper.isUserLogged()) {
                appPreferencesHelper.setAccessToken(null)
                appPreferencesHelper.setCurrentUserId(null);
                appPreferencesHelper.setCurrentUserName(null)
                appPreferencesHelper.setCurrentUserEmail(null)
                appPreferencesHelper.setCurrentUserProfilePicUrl(null)
                appPreferencesHelper.setUserObject(null)
                appPreferencesHelper.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)
                context!!.startActivity(
                    LoginActivity.newIntent(context)
                        .setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                )
            }
            Toasty.error(
                context!!,
                context!!.resources.getString(R.string.session_expired),
                Toast.LENGTH_LONG,
                true
            ).show()
        } else {
            Toasty.error(
                context!!,
                context!!.resources.getString(R.string.some_error),
                Toast.LENGTH_LONG,
                true
            ).show()
        }
    }

}