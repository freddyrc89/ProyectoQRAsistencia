package com.freddy.proyectoqrasistencia

import android.content.pm.ActivityInfo
import android.os.Bundle

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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
fun QRScreen(viewModel: QRViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val showQR by viewModel.showQR.collectAsState()
    val showData by viewModel.showData.collectAsState()

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
            Button(onClick = {viewModel.startCountdown()}) {
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

@Preview(showBackground = true, device = "spec:parent=pixel_5")
@Composable
fun GreetingPreview() {
    ProyectoQRAsistenciaTheme {
//        Greeting("Android")
        QRScreen()
    }
}