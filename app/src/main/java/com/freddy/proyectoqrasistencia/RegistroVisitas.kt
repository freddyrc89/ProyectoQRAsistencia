package com.freddy.proyectoqrasistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.freddy.proyectoqrasistencia.ui.screens.RegistroScreen
import com.freddy.proyectoqrasistencia.ui.theme.ProyectoQRAsistenciaTheme

class RegistroVisitas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoQRAsistenciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroScreen(modifier = Modifier.padding(innerPadding)) // ✅ Se pasa correctamente el padding
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroVisitasPreview() {
    ProyectoQRAsistenciaTheme {
        RegistroScreen()
    }
}

