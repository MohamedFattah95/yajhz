package com.yajhz.ui.main

interface MainNavigator {
    fun handleError(throwable: Throwable)
    fun openLoginActivity()
    fun showMyApiMessage(message: String?)
}
