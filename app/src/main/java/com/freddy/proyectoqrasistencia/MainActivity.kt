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
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

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

        // Codigo QR
        if (showQR) {
            qrBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Código QR",
                    modifier = Modifier.size(200.dp)
                )
            }
//            Box(modifier = Modifier.size(200.dp)) {
//                Text("QR", fontSize = 24.sp)
//
//            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón/Datos
        if (!showQR) {
            Button(onClick = {
                viewModel.startCountdown();
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