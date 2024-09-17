package com.yuri_berezhnyi.abzapp.di

import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationCommand
import com.yuri_berezhnyi.abzapp.ui.core.ui.SimpleUiTarget
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object MainActivityModule {

    @Provides
    fun provideUpdateNavigationTarget(
        target: SimpleUiTarget.Mutable<NavigationCommand>
    ): SimpleUiTarget.Update<NavigationCommand> = target

}