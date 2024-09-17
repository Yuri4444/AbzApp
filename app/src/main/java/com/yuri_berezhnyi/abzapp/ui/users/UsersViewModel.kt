package com.yuri_berezhnyi.abzapp.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.CoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    dispatchers: CoroutineDispatchers,
) : ViewModel() {

    val pagedRequest: Flow<PagingData<UserUi>> = usersRepository.pagedUsers()
        .flowOn(dispatchers.io())
        .cachedIn(viewModelScope)

}