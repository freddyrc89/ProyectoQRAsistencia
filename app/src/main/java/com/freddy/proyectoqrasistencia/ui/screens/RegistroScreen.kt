package com.freddy.proyectoqrasistencia.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freddy.proyectoqrasistencia.model.Invitado
import com.freddy.proyectoqrasistencia.model.RegistroResponse
import com.freddy.proyectoqrasistencia.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegistroScreen(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var mensajeRespuesta by remember { mutableStateOf("") } // Mensaje para mostrar respuesta de la API
    val contexto = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        Titulo()

        Spacer(modifier = Modifier.height(32.dp))

        CampoTexto(label = "Nombre", valor = nombre, onValueChange = { nombre = it })
        Spacer(modifier = Modifier.height(20.dp))
        CampoTexto(label = "Apellidos", valor = apellidos, onValueChange = { apellidos = it })

        Spacer(modifier = Modifier.height(40.dp))

        BotonRegistrar(
            onClick = {
                if (nombre.isNotBlank() && apellidos.isNotBlank()) {
                    registrarInvitado(nombre, apellidos) { mensaje ->
                        mensajeRespuesta = mensaje
                        Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show()
                    }
                } else {
                    mensajeRespuesta = "Completa todos los campos"
                }
            }
        )

        if (mensajeRespuesta.isNotEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = mensajeRespuesta,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Red
            )
        }
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

@Composable
fun CampoTexto(label: String, valor: String, onValueChange: (String) -> Unit) {
    Text(text = label, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    TextField(
        value = valor,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BotonRegistrar(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp)),
    ) {
        Text(text = "Registrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

/**
 * Función para registrar un invitado en la API Flask
 */
fun registrarInvitado(nombre: String, apellido: String, onResult: (String) -> Unit) {
    val invitado = Invitado(nombre, apellido)
    val call = RetrofitClient.apiService.registrarInvitado(invitado)

    call.enqueue(object : Callback<RegistroResponse> {
        override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
            if (response.isSuccessful) {
                onResult("Invitado registrado con éxito")
            } else {
                onResult("Error al registrar: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
            Log.e("API_ERROR", "Error en la API", t)
            onResult("Error de conexión: ${t.message}")
        }
    })
}


