package com.yuri_berezhnyi.abzapp.data.network

import android.util.Log
import com.yuri_berezhnyi.abzapp.ui.utils.AppPreferences
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()
        val headersBuilder: Headers.Builder = original.headers.newBuilder()
        headersBuilder.add("accept", "application/json")
        headersBuilder.add("Token", AppPreferences.accessToken)
        headersBuilder.add("Content-Type", "multipart/form-data")
        Log.e("AuthInterceptor", "intercept ${original.url}")
        original = original.newBuilder().headers(headersBuilder.build()).build()
        return chain.proceed(original)
    }

}