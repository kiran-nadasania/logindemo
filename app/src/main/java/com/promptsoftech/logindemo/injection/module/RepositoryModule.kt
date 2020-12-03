package com.promptsoftech.logindemo.injection.module

import com.promptsoftech.logindemo.MyApplication
import com.promptsoftech.logindemo.database.Database
import com.promptsoftech.logindemo.network.Api
import com.promptsoftech.logindemo.repositories.UserRepository
import com.promptsoftech.logindemo.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(api: Api): UserRepository {
        return UserRepositoryImpl(api)
    }
}