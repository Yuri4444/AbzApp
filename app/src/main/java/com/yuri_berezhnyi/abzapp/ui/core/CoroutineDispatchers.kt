package com.yuri_berezhnyi.abzapp.ui.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun default(): CoroutineDispatcher

    class Main : CoroutineDispatchers {
        override fun io(): CoroutineDispatcher = Dispatchers.IO
        override fun main(): CoroutineDispatcher = Dispatchers.Main
        override fun default(): CoroutineDispatcher = Dispatchers.Default
    }
}