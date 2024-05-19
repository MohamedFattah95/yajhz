package com.yajhz.ui.splash

import com.yajhz.data.DataManager
import com.yajhz.ui.base.BaseViewModel
import com.yajhz.utils.rx.SchedulerProvider

class SplashViewModel(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) :
    BaseViewModel<SplashNavigator?>(dataManager, schedulerProvider) {
    fun decideNextActivity() {
        if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type) {
            navigator?.openIntroActivity()
        } else {
            navigator?.openMainActivity()
        }
    }
}
