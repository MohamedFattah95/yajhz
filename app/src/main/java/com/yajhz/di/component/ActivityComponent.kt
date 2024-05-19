package com.yajhz.di.component

import com.yajhz.di.module.ActivityModule
import com.yajhz.di.scope.ActivityScope
import com.yajhz.ui.error_handler.ErrorHandlerActivity
import com.yajhz.ui.main.MainActivity
import com.yajhz.ui.splash.SplashActivity
import com.yajhz.ui.user.login.LoginActivity
import com.yajhz.ui.user.register.RegisterActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: ErrorHandlerActivity)
}