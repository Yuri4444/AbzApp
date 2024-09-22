package com.yuri_berezhnyi.abzapp.domain

import com.yuri_berezhnyi.abzapp.data.cloud.RegisterUserResponse
import com.yuri_berezhnyi.abzapp.data.cloud.TokenResponse
import com.yuri_berezhnyi.abzapp.data.cloud.UserRequest
import com.yuri_berezhnyi.abzapp.ui.users.PositionUI
import com.yuri_berezhnyi.abzapp.ui.users.UserUi

interface UsersRepository {

    suspend fun pagedUsers(page: Int, count: Int): List<UserUi>?
    suspend fun token(): Result<TokenResponse>
    suspend fun registerUser(userRequest: UserRequest): Result<RegisterUserResponse>
    suspend fun positions(): Result<List<PositionUI>>

}