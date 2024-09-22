package com.yuri_berezhnyi.abzapp.data.cloud

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
)