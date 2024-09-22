package com.yuri_berezhnyi.abzapp.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationCommand
import com.yuri_berezhnyi.abzapp.ui.core.ui.SimpleUiTarget
import com.yuri_berezhnyi.abzapp.ui.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val navigationTarget: SimpleUiTarget.Mutable<NavigationCommand>,
    private val dispatchers: CoroutineDispatchers,
) : ViewModel() {

    init {
        token()
    }

    suspend fun collectNavigationTarget(
        collector: FlowCollector<NavigationCommand>
    ) = navigationTarget.collect(collector)

    private fun token() {
        viewModelScope.launch(dispatchers.io()) {
            usersRepository.token()
                .onSuccess {
                    withContext(dispatchers.main()) {
                        AppPreferences.accessToken = it.token
                    }
                }
        }
    }

}