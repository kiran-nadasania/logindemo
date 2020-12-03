package com.promptsoftech.logindemo.repositories

import com.promptsoftech.logindemo.database.dao.UserDAO
import com.promptsoftech.logindemo.database.entity.UserEntity
import com.promptsoftech.logindemo.models.LoginResponse
import com.promptsoftech.logindemo.requests.LoginRequest
import retrofit2.Response

interface UserRepository {
    fun getUser(loginRequest: LoginRequest, onSuccess: (response: Response<LoginResponse>) -> Unit, onFailure: (t: Throwable) -> Unit)
}