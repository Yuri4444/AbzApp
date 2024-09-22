package com.yuri_berezhnyi.abzapp.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.data.cloud.UserRequest
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.CoroutineDispatchers
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationStrategy
import com.yuri_berezhnyi.abzapp.ui.users.PositionUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val dispatchers: CoroutineDispatchers,
    private val navigation: NavigationStrategy
) : ViewModel() {

    val positions = MutableStateFlow<List<PositionUI>>(emptyList())

    fun fetchPositions() {
        viewModelScope.launch(dispatchers.io()) {
            usersRepository.positions()
                .onSuccess {
                    withContext(dispatchers.main()) {
                        positions.value = it
                    }
                }
                .onFailure {
                    withContext(dispatchers.main()) {

                    }
                }
        }
    }

    fun registrationUser(userRequest: UserRequest) {
        viewModelScope.launch(dispatchers.io()) {
            usersRepository.registerUser(userRequest)
                .onSuccess {
                    withContext(dispatchers.main()) {
                        navigation.navigate(R.id.action_signUpFragment_to_successFragment)
                    }
                }
                .onFailure {
                    withContext(dispatchers.main()) {
                        navigation.navigate(R.id.action_signUpFragment_to_failureFragment)
                    }
                }
        }
    }

}