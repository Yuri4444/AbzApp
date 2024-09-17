package com.yuri_berezhnyi.abzapp.ui.core.errors

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.google.gson.Gson
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
interface HandlePagingResult {

    suspend fun <T> handleMediator(
        responseRequest: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> RemoteMediator.MediatorResult.Success,
    ): RemoteMediator.MediatorResult

    suspend fun <T, R : Any> handle(
        responseRequest: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> PagingSource.LoadResult.Page<Int, R>,
    ): PagingSource.LoadResult<Int, R>

    class Main() : HandlePagingResult {

        private val gson = Gson()

        override suspend fun <T> handleMediator(
            responseRequest: suspend () -> Response<T>,
            onSuccess: suspend (body: T) -> RemoteMediator.MediatorResult.Success
        ): RemoteMediator.MediatorResult {
            return try {
                val response = responseRequest.invoke()
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body()!!)
                } else {
                    RemoteMediator.MediatorResult.Error(UiException.Unauthorized)
                }
            } catch (e: Exception) {
                RemoteMediator.MediatorResult.Error(UiException.NoInternetConnection)
            }
        }

        override suspend fun <T, R : Any> handle(
            responseRequest: suspend () -> Response<T>,
            onSuccess: suspend (body: T) -> PagingSource.LoadResult.Page<Int, R>
        ): PagingSource.LoadResult<Int, R> {
            return try {
                val response = responseRequest.invoke()
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body()!!)
                } else {
                    PagingSource.LoadResult.Error(UiException.Unauthorized)
                }
            } catch (e: Exception) {
                PagingSource.LoadResult.Error(UiException.NoInternetConnection)
            }
        }
    }
}
