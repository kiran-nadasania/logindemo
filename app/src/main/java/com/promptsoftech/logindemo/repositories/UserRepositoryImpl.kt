package com.promptsoftech.logindemo.repositories

import com.promptsoftech.logindemo.models.LoginResponse
import com.promptsoftech.logindemo.network.Api
import com.promptsoftech.logindemo.requests.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(private val api: Api) : UserRepository {

    override fun getUser(loginRequest: LoginRequest, onSuccess: (user: Response<LoginResponse>) -> Unit, onFailure: (t: Throwable) -> Unit) {
        api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.let { user ->
                    onSuccess.invoke(user)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure.invoke(t)
            }
        })
    }
}