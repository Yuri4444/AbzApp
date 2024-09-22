package com.yuri_berezhnyi.abzapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.users.UserUi
import kotlinx.coroutines.delay

class UsersPagingSource(private val usersRepository: UsersRepository) :
    PagingSource<Int, UserUi>() {

    override fun getRefreshKey(state: PagingState<Int, UserUi>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserUi> {
        val page = params.key ?: 1
        return try {
            val entities = usersRepository.pagedUsers(page = page, count = params.loadSize)
            if (page != 0) delay(1000)
            LoadResult.Page(
                data = entities!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}