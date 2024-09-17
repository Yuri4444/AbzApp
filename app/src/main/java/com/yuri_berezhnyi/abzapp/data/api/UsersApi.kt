package com.yuri_berezhnyi.abzapp.data.api

import com.yuri_berezhnyi.abzapp.data.cloud.UserRequest
import com.yuri_berezhnyi.abzapp.data.cloud.UsersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsersApi {

    @GET("users")
    suspend fun users(
        @Query("page") page: Int,
        @Query("count") count: Int,
    ): Response<UsersResponse>

    @POST("users")
    suspend fun registerUser(
        @Body user: UserRequest
    ): Response<Unit>

}