package com.freddy.proyectoqrasistencia.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.freddy.proyectoqrasistencia.R
import com.freddy.proyectoqrasistencia.ui.theme.DarkTextColor
import kotlinx.coroutines.launch

@Composable
fun AuthenticationScreenTemplate(
    modifier: Modifier = Modifier,
    backgroundGradient: Array<Pair<Float, Color>>,
    @DrawableRes imgRes: Int,
    title: String,
    subtitle: String,
    mainActionButtonTitle: String,
    secondaryActionButtonTitle: String,
    mainActionButtonColors: ButtonColors,
    secondaryActionButtonColors: ButtonColors,
    actionButtonShadow: Color,
    onMainActionButtonClicked: () -> Unit,
    onSecondaryActionButtonClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(*backgroundGradient))
            .systemBarsPadding()
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenedor para centrar la imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imgRes),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .padding(10.dp)
            )
        }

        Message(
            title = title,
            subtitle = subtitle
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            leadingIconRes = R.drawable.ic_person,
            placeholderText = "Correo",
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        InputField(
            leadingIconRes = R.drawable.ic_key,
            placeholderText = "Contraseña",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        ActionButton(
            text = mainActionButtonTitle,
            isNavigationArrowVisible = true,
            onClicked = onMainActionButtonClicked,
            colors = mainActionButtonColors,
            shadowColor = actionButtonShadow,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Separator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(62.dp)
        )

        ActionButton(
            text = secondaryActionButtonTitle,
            isNavigationArrowVisible = false,
            onClicked = onSecondaryActionButtonClicked,
            colors = secondaryActionButtonColors,
            shadowColor = actionButtonShadow,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = subtitle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @DrawableRes leadingIconRes: Int,
    placeholderText: String
) {
    var inputValue by remember { mutableStateOf("") }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp),
        value = inputValue,
        onValueChange = { inputValue = it },
        visualTransformation = visualTransformation,
        singleLine = true,
        shape = RoundedCornerShape(percent = 50),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = DarkTextColor,
            unfocusedTextColor = DarkTextColor,
            unfocusedPlaceholderColor = DarkTextColor,
            focusedPlaceholderColor = DarkTextColor,
            focusedLeadingIconColor = DarkTextColor,
            unfocusedLeadingIconColor = DarkTextColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Medium
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        placeholder = {
            Text(text = placeholderText)
        }
    )
}

@Composable
private fun Separator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DashedLine(
            modifier = Modifier.weight(weight = 1f)
        )
        Text(
            text = "O",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White
        )
        DashedLine(
            modifier = Modifier.weight(weight = 1f)
        )
    }
}

@Composable
private fun DashedLine(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        drawLine(
            color = Color.White,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f),
            cap = StrokeCap.Round,
            strokeWidth = 1.dp.toPx()
        )
    }
}
