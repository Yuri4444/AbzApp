package com.yuri_berezhnyi.abzapp.ui.splash

import androidx.lifecycle.ViewModel
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigation: NavigationStrategy
) : ViewModel() {

    fun goUsers() = navigation.navigate(R.id.action_splashFragment_to_usersFragment)
    fun goNoInternet() = navigation.navigate(R.id.action_splashFragment_to_noInternetFragment)


}