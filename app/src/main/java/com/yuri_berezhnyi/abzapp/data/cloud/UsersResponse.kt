package com.yuri_berezhnyi.abzapp.data.cloud

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_users")
    val totalUsers: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("links")
    val links: Links,
    @SerializedName("users")
    val users: List<UserDTO>
)

data class Links(
    @SerializedName("next_url")
    val nextUrl: String,
    @SerializedName("prev_url")
    val prevUrl: String
)

data class UserDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("position_id")
    val positionId: Int,
    @SerializedName("registration_timestamp")
    val registrationTimestamp: Long,
    @SerializedName("photo")
    val photo: String
)