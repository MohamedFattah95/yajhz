package com.yajhz.ui.splash

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import com.yajhz.databinding.ActivitySplashBinding
import com.yajhz.di.component.ActivityComponent
import com.yajhz.ui.base.BaseActivity
import com.yajhz.ui.main.MainActivity
import com.yajhz.ui.user.login.LoginActivity
import java.util.Timer
import kotlin.concurrent.timerTask


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashViewModel>(), SplashNavigator {

    private lateinit var binding: ActivitySplashBinding

    override fun openLoginActivity() {
        startActivity(LoginActivity.newIntent(this@SplashActivity))
        finish()
    }

    override fun openMainActivity() {
        startActivity(MainActivity.newIntent(this@SplashActivity))
        finish()
    }

    override fun openIntroActivity() {
        startActivity(LoginActivity.newIntent(this@SplashActivity))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.setNavigator(this)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        Timer().schedule(timerTask { mViewModel.decideNextActivity() }, 2400)
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

}
