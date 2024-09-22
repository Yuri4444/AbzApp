package com.yuri_berezhnyi.abzapp.di

import com.yuri_berezhnyi.abzapp.data.UserMediator
import com.yuri_berezhnyi.abzapp.data.UsersRepositoryImpl
import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandlePagingResult
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandleRequestResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UsersModule {

    @Provides
    fun provideUserMediator(
        usersApi: UsersApi,
        handlePagingResult: HandlePagingResult
    ): UserMediator = UserMediator(
        usersApi = usersApi,
        handlePagingResult = handlePagingResult
    )

    @Provides
    fun provideUserRepository(
        usersApi: UsersApi,
        handleRequestResult: HandleRequestResult
    ): UsersRepository = UsersRepositoryImpl(
        usersApi = usersApi,
        handleRequestResult = handleRequestResult
    )
}