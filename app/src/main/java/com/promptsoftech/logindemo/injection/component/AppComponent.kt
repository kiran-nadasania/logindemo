package com.promptsoftech.logindemo.injection.component

import com.promptsoftech.logindemo.injection.module.AppModule
import com.promptsoftech.logindemo.injection.module.RepositoryModule
import com.promptsoftech.logindemo.injection.module.ViewModelModule
import com.promptsoftech.logindemo.views.LoginActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    fun inject(activity: LoginActivity)
}