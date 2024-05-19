package com.yajhz.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.provider.Settings
import android.util.Patterns
import android.widget.Button
import android.widget.RatingBar
import androidx.core.graphics.drawable.DrawableCompat
import com.yajhz.R
import com.yajhz.ui.user.login.LoginActivity
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object CommonUtils {
    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    val timestamp: String
        get() = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US)
            .format(Date())

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(pass: String?): Boolean {
        val pattern: Pattern

        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(pass!!)

        return matcher.matches()

    }

    fun handleNotAuthenticated(activity: Context) {
        val loginDialog = Dialog(activity, R.style.FilterDialogTheme)
        loginDialog.setContentView(R.layout.dialog_login)
        loginDialog.setCancelable(true)
        val button = loginDialog.window!!.decorView.findViewById<Button>(R.id.goToLoginButton)
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = intent.flags or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        button.setOnClickListener {
            activity.startActivity(
                intent
            )
        }
        loginDialog.show()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String?): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName!!)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, StandardCharsets.UTF_8)
    }

    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun getLanguageAwareContext(context: Context): Context {
        val configuration =
            context.resources.configuration
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales[0]
        } else {
            configuration.locale
        }
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }


    fun setupRatingBar(context: Context, ratingBarDriver: RatingBar) {
        val layerDrawable = ratingBarDriver.progressDrawable as LayerDrawable
        DrawableCompat.setTint(
            DrawableCompat.wrap(layerDrawable.getDrawable(0)),
            Color.GRAY
        ) // Empty star
        DrawableCompat.setTint(
            DrawableCompat.wrap(layerDrawable.getDrawable(1)),
            context.resources.getColor(R.color.colorGold)
        ) // Partial star
        DrawableCompat.setTint(
            DrawableCompat.wrap(layerDrawable.getDrawable(2)),
            context.resources.getColor(R.color.colorGold)
        )
    }

    fun formatCurrency(number: Double): String {
        val pattern = ",###.##" //your pattern as per need
        val locale = Locale("en", "US")
        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)
        return decimalFormat.format(number)
    }

    fun formatDistance(number: Double): String {
        val pattern = "###.##" //your pattern as per need
        val locale = Locale("en", "US")
        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern)
        return decimalFormat.format(number)
    }


    @SuppressLint("DefaultLocale")
    fun isValidUrl(url: String?): Boolean {
        return if (url != null) {
            val p = Patterns.WEB_URL
            val m = p.matcher(url.toLowerCase())
            m.matches()
        } else false
    }


}
