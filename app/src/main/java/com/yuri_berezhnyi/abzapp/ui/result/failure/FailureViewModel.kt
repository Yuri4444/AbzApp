package com.yuri_berezhnyi.abzapp.ui.result.failure

import androidx.lifecycle.ViewModel
import com.yuri_berezhnyi.abzapp.R
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FailureViewModel @Inject constructor(
    private val navigation: NavigationStrategy
): ViewModel() {

    fun goBack() = navigation.goBack()

}