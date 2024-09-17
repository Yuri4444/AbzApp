package com.yuri_berezhnyi.abzapp.data.network

import android.util.Log
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer

class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "ApiLogger"
        if (message.startsWith("{") || message.startsWith("[")) {
            val source: Buffer = Buffer().writeUtf8(message)
            val reader: JsonReader = JsonReader.of(source)
            val value: Any? = reader.readJsonValue()
            val adapter = Moshi.Builder().build().adapter(Any::class.java).indent("  ")
            val result = adapter.toJson(value)
            result.lines().forEach{ Log.i(logName, it)}
        } else {
            Log.i(logName, message)
            return
        }
    }
}