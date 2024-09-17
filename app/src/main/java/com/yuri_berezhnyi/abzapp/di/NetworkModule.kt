package com.yuri_berezhnyi.abzapp.di

import com.yuri_berezhnyi.abzapp.data.api.UsersApi
import com.yuri_berezhnyi.abzapp.data.network.ApiLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(ApiLogger()).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://frontend-test-assignment-api.abz.agency/api/v1/"
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Provides
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun provideOkHttpClient(logInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(logInterceptor)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): UsersApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(UsersApi::class.java)
    }
}