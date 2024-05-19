package com.yajhz.ui.user.login

interface LoginNavigator {
    fun openMainActivity()
    fun handleError(throwable: Throwable)
    fun showMyApiMessage(message: String?)
}