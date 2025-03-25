package com.freddy.proyectoqrasistencia

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QRViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesHelper = PreferencesHelper(application)

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
}
