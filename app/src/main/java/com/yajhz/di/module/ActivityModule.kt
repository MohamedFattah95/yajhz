package com.yajhz.di.module


import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.yajhz.ViewModelProviderFactory
import com.yajhz.data.DataManager
import com.yajhz.ui.base.BaseActivity
import com.yajhz.ui.error_handler.ErrorHandlerViewModel
import com.yajhz.ui.main.MainViewModel
import com.yajhz.ui.main.adapters.CategoriesAdapter
import com.yajhz.ui.main.adapters.PopularAdapter
import com.yajhz.ui.main.adapters.TrendingAdapter
import com.yajhz.ui.splash.SplashViewModel
import com.yajhz.ui.user.login.LoginViewModel
import com.yajhz.ui.user.register.RegisterViewModel
import com.yajhz.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: BaseActivity<*>?) {

    @Provides
    fun provideCategoriesAdapter(): CategoriesAdapter {
        return CategoriesAdapter(ArrayList())
    }
    @Provides
    fun providePopularAdapter(): PopularAdapter {
        return PopularAdapter(ArrayList())
    }
    @Provides
    fun provideTrendingAdapter(): TrendingAdapter {
        return TrendingAdapter(ArrayList())
    }

    @Provides
    fun provideMainViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): MainViewModel {
        val supplier: Supplier<MainViewModel> =
            Supplier { MainViewModel(dataManager, schedulerProvider) }
        val factory: ViewModelProviderFactory<MainViewModel> =
            ViewModelProviderFactory(MainViewModel::class.java, supplier)
        return ViewModelProvider(this.activity!!, factory).get(MainViewModel::class.java)
    }

    @Provides
    fun provideLoginViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): LoginViewModel {
        val supplier: Supplier<LoginViewModel> =
            Supplier { LoginViewModel(dataManager, schedulerProvider) }
        val factory: ViewModelProviderFactory<LoginViewModel> =
            ViewModelProviderFactory(LoginViewModel::class.java, supplier)
        return ViewModelProvider(this.activity!!, factory).get(LoginViewModel::class.java)
    }

    @Provides
    fun provideSplashViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): SplashViewModel {
        val supplier: Supplier<SplashViewModel> =
            Supplier { SplashViewModel(dataManager, schedulerProvider) }
        val factory: ViewModelProviderFactory<SplashViewModel> =
            ViewModelProviderFactory(SplashViewModel::class.java, supplier)
        return ViewModelProvider(this.activity!!, factory).get(SplashViewModel::class.java)
    }

    @Provides
    fun provideRegisterViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): RegisterViewModel {
        val supplier: Supplier<RegisterViewModel> =
            Supplier { RegisterViewModel(dataManager, schedulerProvider) }
        val factory: ViewModelProviderFactory<RegisterViewModel> =
            ViewModelProviderFactory(RegisterViewModel::class.java, supplier)
        return ViewModelProvider(this.activity!!, factory).get(RegisterViewModel::class.java)
    }

    @Provides
    fun provideErrorHandlerViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): ErrorHandlerViewModel {
        val supplier: Supplier<ErrorHandlerViewModel> =
            Supplier { ErrorHandlerViewModel(dataManager, schedulerProvider) }
        val factory: ViewModelProviderFactory<ErrorHandlerViewModel> =
            ViewModelProviderFactory(ErrorHandlerViewModel::class.java, supplier)
        return ViewModelProvider(this.activity!!, factory).get(ErrorHandlerViewModel::class.java)
    }

}
