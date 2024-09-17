package com.yuri_berezhnyi.abzapp.ui.core.errors

import com.google.gson.Gson
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

interface HandleRequestResult {

    suspend fun <T : Any, R> handle(
        responseRequest: suspend () -> Response<T>,
        onFailure: suspend () -> Unit = { },
        onFailureWithCode: (suspend (code: Int, errorBody: String) -> Result<R>)? = null,
        onSuccess: suspend (body: T) -> Result<R>,
    ): Result<R>

    suspend fun <T : Any, R> handleResponse(
        responseRequest: suspend () -> Response<T>,
        onSuccess: suspend (response: Response<T>) -> Result<R>,
        onFailure: suspend () -> Unit = {},
        onFailureWithCode: (suspend (code: Int, errorBody: String) -> Result<R>)? = null,
    ): Result<R>

    class Main() : HandleRequestResult {

        private val gson = Gson()

        override suspend fun <T : Any, R> handle(
            responseRequest: suspend () -> Response<T>,
            onFailure: suspend () -> Unit,
            onFailureWithCode: (suspend (code: Int, errorBody: String) -> Result<R>)?,
            onSuccess: suspend (body: T) -> Result<R>
        ): Result<R> {
            return baseHandle(
                responseRequest = responseRequest,
                onSuccess = {
                    onSuccess.invoke(it.body()!!)
                },
                onFailure = onFailure,
                onFailureWithCode = onFailureWithCode
            )
        }

        override suspend fun <T : Any, R> handleResponse(
            responseRequest: suspend () -> Response<T>,
            onSuccess: suspend (response: Response<T>) -> Result<R>,
            onFailure: suspend () -> Unit,
            onFailureWithCode: (suspend (code: Int, errorBody: String) -> Result<R>)?
        ): Result<R> {
            return baseHandle(responseRequest, onSuccess, onFailure, onFailureWithCode)
        }

        private suspend fun <T : Any, R> baseHandle(
            responseRequest: suspend () -> Response<T>,
            onSuccess: suspend (response: Response<T>) -> Result<R>,
            onFailure: suspend () -> Unit,
            onFailureWithCode: (suspend (code: Int, errorBody: String) -> Result<R>)?
        ): Result<R> {
            return try {
                val response = responseRequest.invoke()
                if (response.isSuccessful) {
                    onSuccess.invoke(response)
                } else {
                    val code = response.code()
                    if (code == 401) {
                        Result.failure(UiException.Unauthorized)
                    } else if (onFailureWithCode == null) {
                        onFailure.invoke()
                        Result.failure(UiException.BadRequest())
                    } else {
                        onFailureWithCode.invoke(code, response.errorBody()?.string() ?: "")
                    }
                }
            } catch (e: UnknownHostException) {
                onFailure.invoke()
                Result.failure(UiException.NoInternetConnection)
            } catch (e: TimeoutException) {
                onFailure.invoke()
                Result.failure(UiException.Timeout)
            } catch (e: SocketTimeoutException) {
                onFailure.invoke()
                Result.failure(UiException.Timeout)
            } catch (e: Exception) {
                onFailure.invoke()
                Result.failure(UiException.BadRequest())
            }
        }
    }
}
