package com.freddy.proyectoqrasistencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.freddy.proyectoqrasistencia.ui.theme.ProyectoQRAsistenciaTheme
import androidx.compose.ui.graphics.Color

@Composable
fun RegistroScreen(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }

    // Controlador de teclado
    val tecladoController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        Titulo()

        Spacer(modifier = Modifier.height(32.dp))

        CampoTexto(label = "Nombre", valor = nombre, onValueChange = { nombre = it }, tecladoController)
        Spacer(modifier = Modifier.height(20.dp))
        CampoTexto(label = "Apellidos", valor = apellidos, onValueChange = { apellidos = it }, tecladoController)

        Spacer(modifier = Modifier.height(40.dp))

        BotonRegistrar()
    }
}

@Composable
fun Titulo() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Registro de Invitado",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 4.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Campo de texto que abre el teclado automáticamente al recibir foco.
 */
@Composable
fun CampoTexto(
    label: String,
    valor: String,
    onValueChange: (String) -> Unit,
    tecladoController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
    Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    TextField(
        value = valor,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BotonRegistrar() {
    Button(
        onClick = { /* Aquí se manejará el registro en el futuro */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        Text(text = "Registrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    ProyectoQRAsistenciaTheme {
        RegistroScreen()
    }
}

