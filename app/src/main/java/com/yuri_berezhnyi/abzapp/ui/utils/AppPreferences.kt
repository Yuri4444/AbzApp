package com.yuri_berezhnyi.abzapp.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.util.*

object AppPreferences {
    private const val NAME = "AppPrefs"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val ACCESS_TOKEN = SharedPrefPair("access_token", "")
    var accessToken: String
        get() = getter(ACCESS_TOKEN) as String
        set(value) = setter(ACCESS_TOKEN, value)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getter(valuePair: SharedPrefPair): Any? {
        return when (valuePair.defValue) {
            is Boolean -> preferences.getBoolean(valuePair.key, valuePair.defValue)
            is String -> preferences.getString(valuePair.key, valuePair.defValue)
            is Int -> preferences.getInt(valuePair.key, valuePair.defValue)
            is Float -> preferences.getFloat(valuePair.key, valuePair.defValue)
            is Long -> preferences.getLong(valuePair.key, valuePair.defValue)
            is Set<*> -> preferences.getStringSet(valuePair.key, valuePair.defValue as Set<String>)
            else -> valuePair.defValue
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setter(pair: SharedPrefPair, value: Any?) {
        preferences.edit {
            when (value) {
                is Boolean -> it.putBoolean(pair.key, value)
                is Int -> it.putInt(pair.key, value)
                is String -> it.putString(pair.key, value)
                is Float -> it.putFloat(pair.key, value)
                is Long -> it.putLong(pair.key, value)
                is Set<*> -> it.putStringSet(pair.key, value as Set<String>)
                else -> throw Exception()
            }
        }
    }
}

data class SharedPrefPair(var key: String, val defValue: Any)