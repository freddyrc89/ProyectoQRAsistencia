package com.freddy.proyectoqrasistencia

import ApiService
import ApiService.Companion.obtenerAlumnoDesdeAPI
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.freddy.proyectoqrasistencia.ui.theme.ProyectoQRAsistenciaTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ////////////////////////////////////////////////
        setContent {
            val viewModel: QRViewModel = viewModel()
            val dni = remember { mutableStateOf("1234567890") }
            LaunchedEffect(dni.value) {
                viewModel.cargarAlumno(dni.value) // Llamamos a la función con el DNI almacenado
                obtenerAlumno(dni.value) // Llama a la función para imprimir en consola
            }
            val alumno by viewModel.alumno.collectAsState() // 🔥 Observa cambios en alumno
            QRScreen(viewModel)

            //////////////////////////////////////////
        }

    }

    private fun obtenerAlumno(dni: String) {
        obtenerAlumnoDesdeAPI(dni) { resultado ->
            if (resultado != null) {
                Log.d("API_RESPONSE", "Alumno obtenido00: ${resultado.nombre}")
            } else {
                Log.e("API_RESPONSE", "No se pudo obtener el alumno")
            }
        }
    }
}

@Composable
fun QRScreen(viewModel: QRViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val showQR by viewModel.showQR.collectAsState()
    val alumno by viewModel.alumno.collectAsState()
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    qrBitmap = generarQR(alumno?.dni.toString())

    // Simulación de obtención de datos (sustituye con el DNI real si lo tienes)
    LaunchedEffect(Unit) {
        viewModel.cargarAlumno("177799999") // Reemplaza con el DNI del usuario
    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showQR) {
                Text(
                    "Tiempo restante: ${timeLeft}s",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (showQR) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    modifier = Modifier.padding(16.dp)
                ) {
                    qrBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Código QR",
                            modifier = Modifier
                                .size(220.dp)
                                .padding(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!showQR) {
                Button(
                    onClick = { viewModel.startCountdown() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(50.dp)
                        .width(200.dp)
                ) {
                    Text("Generar QR", fontSize = 18.sp)
                }
            } else {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Usuario: ${alumno?.nombre ?: "cargando..."}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("dni: ${alumno?.dni ?: "cargando..."}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("carrera: ${alumno?.programa_estudios?: "cargando..."}", fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)

                    }
                }
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