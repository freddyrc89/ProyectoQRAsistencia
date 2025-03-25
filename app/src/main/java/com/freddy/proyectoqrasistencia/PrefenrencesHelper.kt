package com.freddy.proyectoqrasistencia

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("qr_prefs", Context.MODE_PRIVATE)

    fun saveExpirationTime(expirationTime: Long) {
        sharedPreferences.edit() { putLong("expiration_time", expirationTime) }
    }

    fun getExpirationTime(): Long {
        return sharedPreferences.getLong("expiration_time", 0L)
    }

    fun clearExpirationTime() {
        sharedPreferences.edit() { remove("expiration_time") }
    }
}
