package com.freddy.proyectoqrasistencia

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("qr_prefs", Context.MODE_PRIVATE)

    //Guarda en una key expiration_time el valor del parametro que en este caso seria el tiempo restante
    fun saveExpirationTime(expirationTime: Long) {
        sharedPreferences.edit() { putLong("expiration_time", expirationTime) }
    }

    //Devuelve el valor que tenga expiration_time
    fun getExpirationTime(): Long {
        return sharedPreferences.getLong("expiration_time", 0L)
    }

    //Borra la key expiration_time
    fun clearExpirationTime() {
        sharedPreferences.edit() { remove("expiration_time") }
    }
}
