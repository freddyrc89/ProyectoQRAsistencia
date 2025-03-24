package com.freddy.proyectoqrasistencia

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QRViewModel : ViewModel() {
    private val _timeLeft = MutableStateFlow(180) // Tiempo en segundos
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _showQR = MutableStateFlow(false)
    val showQR: StateFlow<Boolean> = _showQR

    private val _showData = MutableStateFlow(false)
    val showData: StateFlow<Boolean> = _showData

    private var timer: CountDownTimer? = null

    fun startCountdown() {
        if (timer == null) {
            timer = object : CountDownTimer(180_000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _timeLeft.value = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    resetState()
                }
            }.start()
        }

        _showQR.value = true
        _showData.value = true
    }

    private fun resetState() {
        _showQR.value = false
        _showData.value = false
        _timeLeft.value = 180
        timer = null
    }
}
