package com.yuri_berezhnyi.abzapp.data.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandlePagingResult

class UsersPagingSource(
    private val usersApi: UsersApi,
    private val handlePagingResult: HandlePagingResult,
) : PagingSource<Int, UserDTO>() {

    override fun getRefreshKey(state: PagingState<Int, UserDTO>): Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, UserDTO> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val pageSize = params.loadSize.coerceAtMost(PAGE_SIZE)
        return handlePagingResult.handle(
            responseRequest = {
                usersApi.users(page, pageSize)
            },
            onSuccess = { response ->
                val content = response.users
                val nextKey =
                    if (content.size < PAGE_SIZE) null else page + 1
                LoadResult.Page(content, null, nextKey)
            }
        )
    }

    companion object {
        const val PAGE_SIZE = 6
        const val STARTING_PAGE_INDEX = 1
    }
}