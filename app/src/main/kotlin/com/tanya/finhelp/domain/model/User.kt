package com.tanya.finhelp.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String
)
