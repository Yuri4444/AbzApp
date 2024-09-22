package com.yuri_berezhnyi.abzapp.data.cloud

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("message")
    val message: String
)
