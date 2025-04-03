package com.freddy.proyectoqrasistencia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.freddy.proyectoqrasistencia.ui.screen.container.ScreenContainer
import com.freddy.proyectoqrasistencia.ui.theme.LoginAppUiTheme
import android.net.Uri
import android.widget.Toast



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginAppUiTheme {
                ScreenContainer()


            }
        }
    }

}