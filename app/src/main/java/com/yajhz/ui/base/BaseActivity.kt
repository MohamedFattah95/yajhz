package com.yajhz.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.yajhz.MvvmApp
import com.yajhz.R
import com.yajhz.data.local.prefs.AppPreferencesHelper
import com.yajhz.di.component.ActivityComponent
import com.yajhz.di.component.DaggerActivityComponent
import com.yajhz.di.module.ActivityModule
import com.yajhz.ui.user.login.LoginActivity
import com.yajhz.utils.CommonUtils
import com.yajhz.utils.LanguageHelper
import com.yajhz.utils.NetworkUtils
import es.dmoral.toasty.Toasty
import javax.inject.Inject

abstract class BaseActivity<V : BaseViewModel<*>> : AppCompatActivity(), BaseFragment.Callback {

    private var mProgressDialog: ProgressDialog? = null

    @Inject
    lateinit var mViewModel: V

    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String?) {}
    override fun attachBaseContext(newBase: Context) {
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

        CommonUtils.getLanguageAwareContext(newBase)
        super.attachBaseContext(
            LanguageHelper.setLanguage(
                newBase,
                LanguageHelper.getLanguage(newBase).toString()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        CommonUtils.getLanguageAwareContext(this)
        LanguageHelper.setLanguage(this, LanguageHelper.getLanguage(this).toString())
        if (AppPreferencesHelper.instance?.isDarkMode() == true) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun hasPermission(permission: String?): Boolean {
        return checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

    fun openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private val buildComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .appComponent((application as MvvmApp).appComponent)
            .activityModule(ActivityModule(this))
            .build()

    abstract fun performDependencyInjection(buildComponent: ActivityComponent?)

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(
        permissions: Array<String?>?,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    fun getIntentWithClearHistory(c: Class<*>?): Intent {
        val intent = Intent(this, c)
        intent.flags = intent.flags or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        return intent
    }


    protected open fun showApiMessage(m: String?) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show()
    }

    open fun showMessage(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_LONG).show()
    }

    open fun showMessage(m: String?) {
        Toast.makeText(this, m, Toast.LENGTH_LONG).show()
    }

    open fun showErrorMessage(m: String?) {
        Toasty.error(this, m!!, Toast.LENGTH_LONG, false).show()
    }

    open fun showSuccessMessage(m: String?) {
        Toasty.success(this, m!!, Toast.LENGTH_LONG, true).show()
    }

    protected open fun showNoteMessage(m: String?) {
        Toasty.info(this, m!!, Toast.LENGTH_LONG, true).show()
    }


    open fun showNoteDialog(title: String?, note: String?, context: Context?) {
        val factory = LayoutInflater.from(context)
        val dialogView: View = factory.inflate(R.layout.dialog_note, null)
        val mAlertDialog = AlertDialog.Builder(context).create()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAlertDialog.setView(dialogView)
        val btnOk = dialogView.findViewById<Button>(R.id.okBtn)
        val titleTV = dialogView.findViewById<TextView>(R.id.title)
        val subTitleTV = dialogView.findViewById<TextView>(R.id.subTitle)
        titleTV.text = title
        subTitleTV.text = note
        btnOk.setOnClickListener { mAlertDialog.dismiss() }
        mAlertDialog.show()
    }


    open fun isLastActivity(activity: Activity): Boolean {
        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskList = mngr.getRunningTasks(10)
        return taskList[0].numActivities == 1 && taskList[0].topActivity!!.className == activity.javaClass.name
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)
    }


}

