package com.promptsoftech.logindemo.injection.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.promptsoftech.logindemo.MyApplication
import com.promptsoftech.logindemo.injection.qualifier.ApplicationContext
import com.promptsoftech.logindemo.network.Api
import com.promptsoftech.logindemo.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(){

    @Provides
    fun provideRetrofit():Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("IMSI", "357175048449937")
            requestBuilder.header("IMEI", "510110406068589")
            chain.proceed(requestBuilder.build())
        })
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    /*@Provides
    @Singleton
    fun providesDatabase() : Database{
        return Database.getDatabase(myApplication)
    }*/
}