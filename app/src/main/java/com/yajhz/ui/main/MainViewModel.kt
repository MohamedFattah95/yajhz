package com.yajhz.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yajhz.data.DataManager
import com.yajhz.data.model.api.CategoryModel
import com.yajhz.data.model.api.DataWrapperModel
import com.yajhz.data.model.api.SellerModel
import com.yajhz.data.model.api.UserModel
import com.yajhz.ui.base.BaseViewModel
import com.yajhz.utils.rx.SchedulerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator?>(dataManager, schedulerProvider) {

    val popularLiveData: MutableLiveData<DataWrapperModel<List<SellerModel>>> = MutableLiveData()
    val trendingLiveData: MutableLiveData<DataWrapperModel<List<SellerModel>>> = MutableLiveData()
    val categoriesLiveData: MutableLiveData<DataWrapperModel<CategoryModel>> = MutableLiveData()

    fun getCategories() {

        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    dataManager.getCategoriesApiCall()
                }
            }.onSuccess { response ->
                when (response.success) {
                    true -> {
                        categoriesLiveData.postValue(response)
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

    fun getTrending(map: HashMap<String, Any>) {

        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    dataManager.getTrendingApiCall(map)
                }
            }.onSuccess { response ->
                when (response.success) {
                    true -> {
                        trendingLiveData.postValue(response)
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

    fun getPopular(map: HashMap<String, Any>) {

        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    dataManager.getPopularApiCall(map)
                }
            }.onSuccess { response ->
                when (response.success) {
                    true -> {
                        popularLiveData.postValue(response)
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

    fun doFavorite(id: Int?) {

        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    dataManager.doFavoriteApiCall(id)
                }
            }
        }
    }

}

