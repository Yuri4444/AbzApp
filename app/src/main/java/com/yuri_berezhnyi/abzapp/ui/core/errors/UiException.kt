package com.yuri_berezhnyi.abzapp.ui.core.errors

import androidx.annotation.StringRes
import com.yuri_berezhnyi.abzapp.R

sealed class UiException : Exception() {
    object NoInternetConnection : UiException()
    object ServiceUnavailable : UiException()
    data class BadRequest(@StringRes val errorResId: Int = R.string.try_again_later) : UiException()
    data class NotFound(val error: String) : UiException()
    object Unauthorized : UiException()
    object PermissionDenied : UiException()
    object Timeout : UiException()
    object Cancelled : UiException()
}
