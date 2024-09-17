package com.yuri_berezhnyi.abzapp.ui.core.errors

interface HandleError<T> {
    fun handle(e: Exception): T
}
