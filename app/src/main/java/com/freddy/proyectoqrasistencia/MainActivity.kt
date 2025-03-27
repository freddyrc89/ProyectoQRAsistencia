package com.freddy.proyectoqrasistencia

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QRScreen()
        }
    }
}

@Composable
fun QRScreen(viewModel: QRViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val showQR by viewModel.showQR.collectAsState()
    val alumno by viewModel.alumno.collectAsState()
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Simulación de obtención de datos (sustituye con el DNI real si lo tienes)
    LaunchedEffect(Unit) {
        viewModel.cargarAlumno("12345678") // Reemplaza con el DNI del usuario
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Contador
        if (showQR) {
            Text("Tiempo restante: ${timeLeft}s", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Código QR
        if (showQR) {
            qrBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Código QR",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón/Datos
        if (!showQR) {
            Button(onClick = {
                viewModel.startCountdown()
                qrBitmap = generarQR("https://mi-sistema.com/verificacion?id=12345")
            })
            {
                Text("Generar QR")
            }
        } else {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Usuario: ${alumno?.nombre ?: "Cargando..."}", fontSize = 18.sp)
                Text("Programa de Estudios: ${alumno?.programa_estudios ?: "Cargando..."}", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_5")
@Composable
fun GreetingPreview() {
    ProyectoQRAsistenciaTheme {
        QRScreen()
    }
}