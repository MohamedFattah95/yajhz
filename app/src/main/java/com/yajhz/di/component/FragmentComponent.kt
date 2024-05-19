package com.yajhz.di.component

import com.yajhz.di.module.FragmentModule
import com.yajhz.di.scope.FragmentScope
import dagger.Component

@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [AppComponent::class])
interface FragmentComponent {
}