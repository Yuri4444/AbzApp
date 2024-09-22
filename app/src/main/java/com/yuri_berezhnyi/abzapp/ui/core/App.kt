package com.yuri_berezhnyi.abzapp.ui.core

import android.app.Application
import com.yuri_berezhnyi.abzapp.ui.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}