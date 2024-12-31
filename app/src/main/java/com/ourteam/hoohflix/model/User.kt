package com.ourteam.hoohflix.model

import com.google.gson.annotations.SerializedName

data class ResponseBody(
    val success: Boolean,
    val message: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val username: String,
    val email: String,
    val password: String
)
