package com.freddy.proyectoqrasistencia

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoQRAsistenciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoQRAsistenciaTheme {
        Greeting("Android")
    }
}
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var textView: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        textView = findViewById(R.id.textView)
//
//        // Llamada a la API en un hilo diferente usando coroutines
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Llamada a la API para obtener un usuario
//                val user = RetrofitInstance.api.getUser(1)
//                // Regresar al hilo principal para actualizar la UI
//                withContext(Dispatchers.Main) {
//                    textView.text = "ID: ${user.id}, Name: ${user.name}, Email: ${user.email}"
//                }
//            } catch (e: Exception) {
//                // Manejar cualquier error que ocurra
//                withContext(Dispatchers.Main) {
//                    textView.text = "Error: ${e.message}"
//                }
//            }
//        }
//    }
//}
