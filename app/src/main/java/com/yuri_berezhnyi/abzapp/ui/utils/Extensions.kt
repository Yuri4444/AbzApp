package com.yuri_berezhnyi.abzapp.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.isNetworkAvailable(): Boolean {
    val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val internetInfo = conManager.activeNetworkInfo
    return internetInfo != null && internetInfo.isConnected
}