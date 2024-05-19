package com.yajhz.di.component

import android.app.Application
import com.yajhz.MvvmApp
import com.yajhz.data.DataManager
import com.yajhz.di.module.AppModule
import com.yajhz.utils.rx.SchedulerProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MvvmApp)
    val dataManager: DataManager
    val schedulerProvider: SchedulerProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
