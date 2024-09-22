package com.yuri_berezhnyi.abzapp.ui.utils

import java.util.regex.Pattern

object Validation {

    private const val REGEXP_EMAIL =
        "[a-zA-Z0-9]{1,128}[a-zA-Z0-9!#$%&'*+-/=?^_`{|.]{1,128}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"

    fun validateEmail(email: String?) = if (email.isNullOrBlank()) false
    else Pattern.compile(REGEXP_EMAIL).matcher(email).matches()
}