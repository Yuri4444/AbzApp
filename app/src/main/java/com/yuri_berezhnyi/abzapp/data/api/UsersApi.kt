package com.yuri_berezhnyi.abzapp.data.api

import com.yuri_berezhnyi.abzapp.data.cloud.PositionResponse
import com.yuri_berezhnyi.abzapp.data.cloud.RegisterUserResponse
import com.yuri_berezhnyi.abzapp.data.cloud.TokenResponse
import com.yuri_berezhnyi.abzapp.data.cloud.UsersResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UsersApi {

    @GET("users")
    suspend fun users(
        @Query("page") page: Int,
        @Query("count") count: Int,
    ): Response<UsersResponse>

    @GET("token")
    suspend fun token(): Response<TokenResponse>

    @POST("users")
    @Multipart
    suspend fun registerUser(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("position_id") positionId: RequestBody,
        @Part photo: MultipartBody.Part
    ): Response<RegisterUserResponse>

    @GET("positions")
    suspend fun position(): Response<PositionResponse>

}