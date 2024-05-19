package com.yajhz.ui.user.register

interface RegisterNavigator {
    fun handleError(throwable: Throwable)
    fun showMyApiMessage(message: String?)
}