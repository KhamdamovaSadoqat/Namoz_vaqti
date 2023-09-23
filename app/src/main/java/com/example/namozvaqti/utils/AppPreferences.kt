package com.example.namozvaqti.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref constructor(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var city: String?
        get() = preferences.getString("city", "Tashkent")
        set(value) = preferences.edit {
            if (value != null) it.putString("city", value)
        }

    var longitude: String?
        get() = preferences.getString("longitude", "69.2797")
        set(value) = preferences.edit {
            if (value != null) it.putString("longitude", value)
        }

    var latitude: String?
        get() = preferences.getString("latitude", "41.3111")
        set(value) = preferences.edit {
            if (value != null) it.putString("latitude", value)
        }


    companion object {
        private const val NAME = "NAMOZ_VAQTI"
        private const val MODE = Context.MODE_PRIVATE
    }
}