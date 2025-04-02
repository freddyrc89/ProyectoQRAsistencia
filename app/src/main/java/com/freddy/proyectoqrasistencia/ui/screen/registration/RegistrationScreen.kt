package com.androidlead.loginappui.ui.screen.registration

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import com.androidlead.loginappui.R
import com.androidlead.loginappui.ui.components.AuthenticationScreenTemplate
import com.androidlead.loginappui.ui.theme.PrimaryViolet
import com.androidlead.loginappui.ui.theme.PrimaryVioletDark
import com.androidlead.loginappui.ui.theme.PrimaryVioletLight
import androidx.compose.material3.ButtonDefaults

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onRegisterClicked: () -> Unit = {},
    onLoginClicked: () -> Unit = {}
) {
    AuthenticationScreenTemplate(
        modifier = modifier,
        backgroundGradient = arrayOf(
            0f to PrimaryViolet,
            1f to PrimaryVioletDark
        ),
        imgRes = R.drawable.guardia,
        title = "Hola, vigilante!",
        subtitle = "Por favor, inicia sesión",
        mainActionButtonTitle = "ACCEDER",
        secondaryActionButtonTitle = "Modo Estudiante",
        mainActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryVioletDark,
            contentColor = Color.White
        ),
        secondaryActionButtonColors = ButtonDefaults.buttonColors(
            containerColor = PrimaryVioletLight,
            contentColor = Color.White
        ),
        actionButtonShadow = PrimaryVioletDark,
        onMainActionButtonClicked = onRegisterClicked,
        onSecondaryActionButtonClicked = onLoginClicked
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(modifier = Modifier.fillMaxSize())
}
