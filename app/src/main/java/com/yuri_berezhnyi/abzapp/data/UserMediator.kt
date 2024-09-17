package com.yuri_berezhnyi.abzapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.data.mapper.userDtoToUserCache
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandlePagingResult
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class UserMediator(
    private val usersApi: UsersApi,
    private val handlePagingResult: HandlePagingResult,
) : RemoteMediator<Int, UserCache>() {

    private var page = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserCache>
    ): MediatorResult {
        page = getPageIndex(loadType) ?: return MediatorResult.Success(true)
        val pageSize = state.config.pageSize

        return handlePagingResult.handleMediator(
            responseRequest = {
                usersApi.users(page, pageSize)
            },
            onSuccess = {
                delay(4000)
                val cache = it.users.map {
                    userDtoToUserCache(it)
                }
                MediatorResult.Success(endOfPaginationReached = cache.size < pageSize)
            }
        )
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++page
        }
        return page
    }

    companion object {
        const val PAGE_SIZE = 6
    }


}