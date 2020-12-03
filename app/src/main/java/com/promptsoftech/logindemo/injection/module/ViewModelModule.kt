package com.promptsoftech.logindemo.injection.module

import com.promptsoftech.logindemo.database.Database
import com.promptsoftech.logindemo.factory.MainViewModelFactory
import com.promptsoftech.logindemo.repositories.UserRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainViewModelFactory(userRepository: UserRepository): MainViewModelFactory {
        return MainViewModelFactory(userRepository)
    }
}