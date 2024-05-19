package com.yajhz.ui.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yajhz.data.DataManager
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.data.model.api.LoginModel
import com.yajhz.ui.base.BaseViewModel
import com.yajhz.utils.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<LoginNavigator?>(dataManager, schedulerProvider) {

    val loginLiveData: MutableLiveData<DataWrapperModel<LoginModel>> = MutableLiveData()

    fun doLogin(body: HashMap<String, String>) {

        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    dataManager.doLoginApiCall(body)
                }
            }.onSuccess { response ->
                when (response.success) {
                    true -> {
                        dataManager.updateUserInfo(
                            response.response,
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        )
                        loginLiveData.postValue(response)
                    }

                    false -> {
                        navigator?.showMyApiMessage(response.message)
                    }

                }


            }.onFailure {
                navigator?.handleError(it)
            }
        }

    }

}
