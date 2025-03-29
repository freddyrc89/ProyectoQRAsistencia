package com.freddy.proyectoqrasistencia

import ApiService.Companion.obtenerAlumnoDesdeAPI
import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QRViewModel(application: Application) : AndroidViewModel(application) {



    private val preferencesHelper = PreferencesHelper(application)
    //
    // Nueva variable para almacenar los datos del alumno
    private val _alumno = MutableStateFlow<Alumno?>(null)
    val alumno: StateFlow<Alumno?> = _alumno.asStateFlow()




    ///
    private val _timeLeft = MutableStateFlow(0)
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _showQR = MutableStateFlow(false)
    val showQR: StateFlow<Boolean> = _showQR

    private var timer: CountDownTimer? = null






    init {
        checkRemainingTime()
    }

    fun startCountdown() {
        val expirationTime = System.currentTimeMillis() + (3 * 60 * 1000) // 3 minutos desde ahora
        preferencesHelper.saveExpirationTime(expirationTime)

        startTimer(expirationTime)
        _showQR.value = true
    }

    private fun checkRemainingTime() {
        val expirationTime = preferencesHelper.getExpirationTime()
        val currentTime = System.currentTimeMillis()
        val remainingTime = ((expirationTime - currentTime) / 1000).toInt()

        if (remainingTime > 0) {
            startTimer(expirationTime)
            _showQR.value = true
        } else {
            _showQR.value = false
            _timeLeft.value = 0
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
    /////////////////////////////////////////////////////////
    // Nueva función para cargar los datos del alumno desde la API
    fun cargarAlumno(dni: String) {
        obtenerAlumnoDesdeAPI(dni) { resultado ->
            if (resultado != null) {
                _alumno.value = resultado // Actualiza el estado
            }
        }
    }
    fun detener(){
        timer?.cancel()
    }
    /////////////////////////////////
}

