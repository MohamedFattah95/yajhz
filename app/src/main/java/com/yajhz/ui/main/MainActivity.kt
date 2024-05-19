package com.yajhz.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.yajhz.MvvmApp
import com.yajhz.databinding.ActivityMainBinding
import com.yajhz.di.component.ActivityComponent
import com.yajhz.ui.base.BaseActivity
import com.yajhz.ui.main.adapters.CategoriesAdapter
import com.yajhz.ui.main.adapters.PopularAdapter
import com.yajhz.ui.main.adapters.TrendingAdapter
import com.yajhz.ui.user.login.LoginActivity
import com.yajhz.utils.ErrorHandlingUtils
import com.yajhz.utils.GpsUtils
import com.yajhz.utils.LocationUtils
import io.nlopez.smartlocation.SmartLocation
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>(), MainNavigator, PopularAdapter.Callback {

    private val REQUEST_STORAGE_CODE = 111
    private val REQUEST_LOCATION_PERMISSION = 1

    @Inject
    lateinit var categoryAdapter: CategoriesAdapter

    @Inject
    lateinit var popularAdapter: PopularAdapter

    @Inject
    lateinit var trendingAdapter: TrendingAdapter

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.setNavigator(this)
        popularAdapter.setCallback(this)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        setUp()

    }

    override fun handleError(throwable: Throwable) {
        binding.refresh.isRefreshing = false
        hideLoading()
        ErrorHandlingUtils.handleErrors(throwable)

    }

    override fun showMyApiMessage(message: String?) {
        binding.refresh.isRefreshing = false
        hideLoading()
        showErrorMessage(message)
    }

    override fun openLoginActivity() {
        startActivity(getIntentWithClearHistory(LoginActivity::class.java))
        finish()
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setUp() {

        binding.tvHi.text = "Hello, ${mViewModel.dataManager.getCurrentUserName()}"

        setSupportActionBar(binding.toolbar.toolbarHome)
        binding.rvCategories.adapter = categoryAdapter
        binding.rvPopular.adapter = popularAdapter
        binding.rvTrending.adapter = trendingAdapter

        initOnClick()
        subscribeToLiveData()

        val map: HashMap<String, Any> = HashMap()

        map["lat"] = MvvmApp.currentLat
        map["lng"] = MvvmApp.currentLong
        map["filter"] = 1

        showLoading()
        mViewModel.getCategories()
        mViewModel.getPopular(map)
        mViewModel.getTrending(map)

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            mViewModel.getCategories()
            mViewModel.getPopular(map)
            mViewModel.getTrending(map)

        }

    }

    private fun initOnClick() {

        binding.toolbar.backButton.setOnClickListener { finish() }

    }

    override fun onResume() {
        super.onResume()

        if (!checkLocationPermission())
            requestLocationPermission()
        else
            gpsCheck()

        binding.tvAddress.text =
            LocationUtils.getAddress(MvvmApp.currentLat, MvvmApp.currentLong, this)

    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private fun subscribeToLiveData() {

        mViewModel.categoriesLiveData.observe(this) {
            binding.refresh.isRefreshing = false
            hideLoading()
            categoryAdapter.clearItems()
            categoryAdapter.addItems(it.response?.data)
        }

        mViewModel.popularLiveData.observe(this) {
            binding.refresh.isRefreshing = false
            hideLoading()
            popularAdapter.clearItems()
            popularAdapter.addItems(it.response)
        }

        mViewModel.trendingLiveData.observe(this) {
            binding.refresh.isRefreshing = false
            hideLoading()
            trendingAdapter.clearItems()
            trendingAdapter.addItems(it.response)
        }

    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        // optional implementation of shouldShowRequestPermissionRationale
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            AlertDialog.Builder(baseContext)
                .setMessage("Need location permission to get current place")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (LocationUtils.isGPSEnabled(this)) {
                    SmartLocation.with(this).location().oneFix().start { location: Location ->

                        MvvmApp.currentLat = location.latitude
                        MvvmApp.currentLong = location.longitude

                        binding.tvAddress.text =
                            LocationUtils.getAddress(location.latitude, location.longitude, this)

                    }
                } else {
                    showMessage("Open GPS")
                }


            } else {
                //Location permission denied
                val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (isNeverAskAgain) {
                    Snackbar.make(
                        binding.activityMain,
                        "Location Permission required",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Settings") {
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:${this.packageName}")
                            ).apply {
                                addCategory(Intent.CATEGORY_DEFAULT)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(this)
                            }
                        }
                        .show()
                }
            }
        } else if (requestCode == REQUEST_STORAGE_CODE) {
//            (navigationFragments[4] as ProfileFragment).myOnRequestPermissionResult(
//                requestCode, permissions,
//                grantResults
//            )
        }
    }

    private fun gpsCheck() {
        if (!LocationUtils.isGPSEnabled(this)) {
            GpsUtils(this).turnGPSOn {}
        }
    }

    override fun doFavorite(id: Int?) {

        mViewModel.doFavorite(id)

    }

}
