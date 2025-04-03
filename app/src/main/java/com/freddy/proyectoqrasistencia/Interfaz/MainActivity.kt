package com.freddy.proyectoqrasistencia.Interfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.freddy.proyectoqrasistencia.ui.theme.InterfazTheme
import kotlinx.coroutines.launch
import com.freddy.proyectoqrasistencia.R
import io.ktor.client.HttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterfazTheme {
                S
            }
        }
    }
}

enum class TipoUsuario {
    ALUMNO,
    VIGILANTE
}


@Composable
fun Screen(modifier: Modifier = Modifier,
           tipoUsuarioAutenticado: TipoUsuario,
           client: HttpClient) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    when (tipoUsuarioAutenticado) {
        TipoUsuario.ALUMNO -> {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier.background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer,
                                )
                            )
                        )
                    ) {
                        DrawerContentAlumno()
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    }
                ) { padding ->
                    ScreenContentAlumno(modifier = Modifier.padding(padding))
                }
            }
        }
        TipoUsuario.VIGILANTE -> {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier.background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer,
                                )
                            )
                        )
                    ) {
                        DrawerContentVigilante()
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )
                    }
                ) { padding ->
                    ScreenContentVigilante(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun DrawerContentAlumno(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.banner),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "Senati Alumno",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    HorizontalDivider()
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Menu",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Menu",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Generar QR",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Generar QR",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "Cerrar Sesion",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Cerrar Sesion",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
}

@Composable
fun HorizontalDivider() {
    TODO("Not yet implemented")
}

@Composable
fun ScreenContentAlumno(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.vigilante),
                contentDescription = "Logo de la app",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Bienvenido Alumno",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painter = painterResource(R.drawable.tonybot),
            contentDescription = "TonyBot",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(25.dp)
                .size(100.dp)
        )
    }
}

@Composable
fun DrawerContentVigilante(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.banner),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "Senati Vigilante",
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    HorizontalDivider()
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Inicio",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Inicio",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "Escanear QR",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Escanear QR",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Agregar Visitante",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Agregar Visitante",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    NavigationDrawerItem(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "Cerrar Sesion",
                modifier = Modifier.size(27.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        label = {
            Text(
                text = "Cerrar Sesion",
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        selected = false,
        onClick = { /*TODO*/ },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    )
}

@Composable
fun ScreenContentVigilante(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.vigilante),
                contentDescription = "Logo de la app",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Bienvenido Vigilante",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(28.dp)
                    .clickable {
                        onOpenDrawer()
                    }
            )
        },
        title = {
            Text(text = "Menu")
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Menu",
                modifier = Modifier.size(30.dp)
            )
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(30.dp)
            )
        }
    )
}