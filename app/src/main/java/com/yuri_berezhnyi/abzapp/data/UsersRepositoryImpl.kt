package com.yuri_berezhnyi.abzapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yuri_berezhnyi.abzapp.data.UserMediator.Companion.PAGE_SIZE
import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.data.cloud.UsersPagingSource
import com.yuri_berezhnyi.abzapp.data.mapper.userDtoToUserUi
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandlePagingResult
import com.yuri_berezhnyi.abzapp.ui.users.UserUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl(
    private val usersApi: UsersApi,
    private val handlePagingResult: HandlePagingResult,
) : UsersRepository {

    override fun pagedUsers(): Flow<PagingData<UserUi>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { UsersPagingSource(usersApi, handlePagingResult) }
        ).flow.map { pagingData ->
            pagingData.map { cloud ->
                userDtoToUserUi(cloud)
            }
        }
    }
}