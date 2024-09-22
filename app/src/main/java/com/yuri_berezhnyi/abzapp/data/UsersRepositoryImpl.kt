package com.yuri_berezhnyi.abzapp.data

import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.data.cloud.RegisterUserResponse
import com.yuri_berezhnyi.abzapp.data.cloud.TokenResponse
import com.yuri_berezhnyi.abzapp.data.cloud.UserRequest
import com.yuri_berezhnyi.abzapp.data.mapper.userDtoToUserUi
import com.yuri_berezhnyi.abzapp.domain.UsersRepository
import com.yuri_berezhnyi.abzapp.ui.core.errors.HandleRequestResult
import com.yuri_berezhnyi.abzapp.ui.core.errors.UiException
import com.yuri_berezhnyi.abzapp.ui.users.PositionUI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UsersRepositoryImpl(
    private val usersApi: UsersApi,
    private val handleRequestResult: HandleRequestResult
) : UsersRepository {

    override suspend fun pagedUsers(page: Int, count: Int) =
        usersApi.users(page = page, count = count).body()?.users?.map { userDtoToUserUi(it) }

    override suspend fun token(): Result<TokenResponse> {
        return handleRequestResult.handle(
            responseRequest = {
                usersApi.token()
            },
            onSuccess = {
                Result.success(it)
            },
            onFailureWithCode = { _, _ ->
                Result.failure(UiException.BadRequest())
            }
        )
    }

    override suspend fun registerUser(userRequest: UserRequest): Result<RegisterUserResponse> {

        val name = userRequest.name.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = userRequest.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = userRequest.phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val positionId = userRequest.positionId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val photoFile = userRequest.photo
        val photoRequestBody = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("photo", photoFile.name, photoRequestBody)

        return handleRequestResult.handle(
            responseRequest = {
                usersApi.registerUser(
                    name = name,
                    email = email,
                    phone = phone,
                    positionId = positionId,
                    photo = photoPart
                )
            },
            onSuccess = {
                Result.success(it)
            },
            onFailureWithCode = { _, _ ->
                Result.failure(UiException.BadRequest())
            }
        )

    }

    override suspend fun positions(): Result<List<PositionUI>> {
        return handleRequestResult.handle(
            responseRequest = {
                usersApi.position()
            },
            onSuccess = { response ->
                val ui = response.positionDto.map { cloud ->
                    userDtoToUserUi(cloud)
                }
                Result.success(ui)
            },
            onFailure = {

            }
        )
    }
}