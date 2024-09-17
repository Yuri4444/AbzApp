package com.yuri_berezhnyi.abzapp.ui.users

data class UserUi(
//    val name: String,
//    val avatar: String,
//    val position: String,
//    val email: String,
//    val phoneNumber: String

    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val position: String,
    val positionId: Int,
    val registrationTimestamp: Long,
    val photo: String
)