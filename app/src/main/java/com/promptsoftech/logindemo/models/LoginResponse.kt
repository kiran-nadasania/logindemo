package com.promptsoftech.logindemo.models

data class LoginResponse(
    val errorCode: String,
    val errorMessage: String,
    val user: User
) {
    data class User(
        val userId: String,
        val userName: String
    )
}