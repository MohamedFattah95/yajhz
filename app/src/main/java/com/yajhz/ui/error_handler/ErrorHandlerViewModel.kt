package com.yajhz.ui.error_handler

import com.yajhz.data.DataManager
import com.yajhz.ui.base.BaseViewModel
import com.yajhz.utils.rx.SchedulerProvider

class ErrorHandlerViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<ErrorHandlerNavigator?>(dataManager, schedulerProvider) {

}
