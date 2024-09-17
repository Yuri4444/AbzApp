package com.yuri_berezhnyi.abzapp.ui.core

import androidx.lifecycle.ViewModel
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationCommand
import com.yuri_berezhnyi.abzapp.ui.core.ui.SimpleUiTarget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val navigationTarget: SimpleUiTarget.Mutable<NavigationCommand>,
) : ViewModel() {

    suspend fun collectNavigationTarget(
        collector: FlowCollector<NavigationCommand>
    ) = navigationTarget.collect(collector)

}