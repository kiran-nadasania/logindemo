package com.promptsoftech.logindemo

import android.app.Application
import androidx.room.Database
import com.promptsoftech.logindemo.injection.component.AppComponent
import com.promptsoftech.logindemo.injection.component.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class MyApplication : Application(){

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .build()
    }

    public fun getComponent() : DaggerAppComponent = component as DaggerAppComponent

}