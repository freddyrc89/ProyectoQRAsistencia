package com.freddy.proyectoqrasistencia

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.freddy.proyectoqrasistencia.ui.theme.ProyectoQRAsistenciaTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            QRScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun QRScreen() {
    var timeLeft by remember { mutableStateOf(180) } // 3 minutos en segundos
    var showQR by remember { mutableStateOf(false) }
    var showData by remember { mutableStateOf(false) }
    val timer = remember {
        object : CountDownTimer(180_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
                showQR = false
                showData = false
                timeLeft = 180
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Contador
        Text("Tiempo restante: ${timeLeft}s", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Codigo QR
        if (showQR) {
            Box(modifier = Modifier.size(200.dp)) {
                Text("QR", fontSize = 24.sp)

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón/Datos
        if (!showData) {
            Button(onClick = {
                showQR = true
                showData = true
                timer.start()
            }) {
                Text("Mostrar Datos")
            }
        } else {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Usuario: Juan Perez", fontSize = 18.sp)
                Text("Email: juan.perez@email.com", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoQRAsistenciaTheme {
//        Greeting("Android")
        QRScreen()
    }
}