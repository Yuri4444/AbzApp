package com.yuri_berezhnyi.abzapp.di

import com.yuri_berezhnyi.abzapp.ui.core.CoroutineDispatchers
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandlePagingResult
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandleRequestResult
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationCommand
import com.yuri_berezhnyi.abzapp.ui.core.ui.NavigationStrategy
import com.yuri_berezhnyi.abzapp.ui.core.ui.SimpleUiTarget
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoreModule {

    @Singleton
    @Provides
    fun provideNavigationTarget(): SimpleUiTarget.Mutable<NavigationCommand> = NavigationTarget()

    @Provides
    @Singleton
    fun provideNavigation(
        navigationTarget: SimpleUiTarget.Update<NavigationCommand>,
    ): NavigationStrategy = NavigationStrategy.Main(navigationTarget)

    class NavigationTarget : SimpleUiTarget.SingleUi<NavigationCommand>()

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers.Main()

    @Provides
    @Singleton
    fun provideHandlePaginationResult(): HandlePagingResult = HandlePagingResult.Main()

    @Provides
    @Singleton
    fun provideHandleResult(): HandleRequestResult = HandleRequestResult.Main()

}