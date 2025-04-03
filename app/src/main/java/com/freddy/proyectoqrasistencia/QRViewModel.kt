package com.freddy.proyectoqrasistencia

import ApiService.Companion.obtenerAlumnoDesdeAPI //obtener daots desde api
import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QRViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesHelper = PreferencesHelper(application)

    private val _alumno = MutableStateFlow<Alumno?>(null)//variable para modificar dentro del VM
    val alumno: StateFlow<Alumno?> = _alumno.asStateFlow()//variable para que clases externas accedan a ellas

    private val _timeLeft = MutableStateFlow(0)
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _showQR = MutableStateFlow(false)
    val showQR: StateFlow<Boolean> = _showQR

    private var timer: CountDownTimer? = null

    //Al ser invocada la clase se ejecuta el metodo para calcular cuanto tiempo restante queda
    init {
        checkRemainingTime()
    }


    fun startCountdown() {
        val expirationTime = System.currentTimeMillis() + (3 * 60 * 1000) // 3 minutos desde ahora
        preferencesHelper.saveExpirationTime(expirationTime)// Guarda en SharedPreferences
        //Inicia el contador con el tiempo restante y muestra el qr
        startTimer(expirationTime)
        _showQR.value = true
    }

    private fun checkRemainingTime() {
        //Obtiene el tiempo restante
        val expirationTime = preferencesHelper.getExpirationTime()// en caso no exista duvuelve 0
        val currentTime = System.currentTimeMillis()

        // Calcula cuanto tiempo queda en expirationTime
        val remainingTime = ((expirationTime - currentTime) / 1000).toInt()

        //si aun queda tiempo mantiene la vista del QR caso contrario oculta el QR
        if (remainingTime > 0) {
            startTimer(expirationTime)
            _showQR.value = true
        } else {
            _showQR.value = false
            _timeLeft.value = 0 //coloca el tiempo restante en 0
        }
    }

    private fun startTimer(expirationTime: Long) {
        val remainingMillis = expirationTime - System.currentTimeMillis()

        timer?.cancel()
        timer = object : CountDownTimer(remainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                _timeLeft.value = 0
                _showQR.value = false
                preferencesHelper.clearExpirationTime()
            }
        }.start()
    }

    fun detener() {
        timer?.cancel() // Cancela el temporizador si está en ejecución
        _timeLeft.value = 0 // Reinicia el contador a su estado inicial
        _showQR.value = false // Oculta el QR
        preferencesHelper.clearExpirationTime() // Borra el tiempo restante
    }

    /////////////////////////////////////////////////////////
    // Nueva función para cargar los datos del alumno desde la API
    fun cargarAlumno(dni: String) {
        obtenerAlumnoDesdeAPI(dni) { resultado ->
            if (resultado != null) {
                _alumno.value = resultado // Actualiza el estado
            }
        }
    }


    /////////////////////////////////
    fun enviarDatosQR() {
        val dni = _alumno.value?.dni?.toIntOrNull() ?: return
        ApiService.enviarDatosQR(dni, true) { exito ->
            if (exito) {
                Log.d("API_RESPONSE", "Datos enviados correctamente")
            } else {
                Log.e("API_RESPONSE", "Error al enviar datos")
            }
        }
    }
}

