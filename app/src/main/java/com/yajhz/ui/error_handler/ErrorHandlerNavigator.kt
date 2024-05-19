package com.yajhz.ui.error_handler

interface ErrorHandlerNavigator {
    fun handleError(throwable: Throwable)
    fun showMyApiMessage(message: String?)
    fun showUserDeletedMsg()
}