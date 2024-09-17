package com.yuri_berezhnyi.abzapp.data

data class UserCache(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val position: String,
    val positionId: Int,
    val registrationTimestamp: Long,
    val photo: String
)
