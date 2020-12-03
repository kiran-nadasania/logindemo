package com.promptsoftech.logindemo.network

import com.promptsoftech.logindemo.models.LoginResponse
import com.promptsoftech.logindemo.requests.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}