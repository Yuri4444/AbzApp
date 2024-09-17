package com.yuri_berezhnyi.abzapp.domain

import androidx.paging.PagingData
import com.yuri_berezhnyi.abzapp.ui.users.UserUi
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun pagedUsers(): Flow<PagingData<UserUi>>

}