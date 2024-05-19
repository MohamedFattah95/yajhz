package com.yajhz.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LanguageUtils {
    fun changeLocalization(
        context: Context,
        lang: String?
    ): Context {
        val res = context.resources
        val dm = res.displayMetrics
        val config = res.configuration
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale)
        } else {
            setSystemLocaleLegacy(config, locale)
        }
        return context.createConfigurationContext(config)
    }

    private fun setSystemLocaleLegacy(
        config: Configuration,
        locale: Locale
    ) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun setSystemLocale(
        config: Configuration,
        locale: Locale
    ) {
        config.setLocale(locale)
    }
}
