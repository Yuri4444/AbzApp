package com.yuri_berezhnyi.abzapp.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yuri_berezhnyi.abzapp.data.UsersPagingSource
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    dispatchers: CoroutineDispatchers,
) : ViewModel() {

    val users = Pager(
        PagingConfig(
            pageSize = 6,
            enablePlaceholders = false,
            initialLoadSize = 6
        )
    ) {
        UsersPagingSource(usersRepository = usersRepository)
    }.flow
        .flowOn(dispatchers.io())
        .cachedIn(viewModelScope)

}