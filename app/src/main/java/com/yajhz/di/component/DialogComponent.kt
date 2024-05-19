package com.yajhz.di.component

import com.yajhz.di.module.DialogModule
import com.yajhz.di.scope.DialogScope
import dagger.Component

@DialogScope
@Component(modules = [DialogModule::class], dependencies = [AppComponent::class])
interface DialogComponent {
}