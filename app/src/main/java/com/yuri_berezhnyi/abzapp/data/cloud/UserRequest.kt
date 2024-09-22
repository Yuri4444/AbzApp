package com.yuri_berezhnyi.abzapp.data.cloud

import com.google.gson.annotations.SerializedName
import java.io.File

data class UserRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("position_id")
    val positionId: Int,
    @SerializedName("photo")
    val photo: File
)
