package com.pad.dishmatch

import CalendarioScreen
import ConocerScreen
import MapaScreen
import PerfilScreen
import RestauranteScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pad.dishmatch.ui.home.PaginaPrincipal
import com.pad.dishmatch.ui.home.PaginaPrincipal
import org.jetbrains.compose.resources.painterResource

import dishmatch.shared.generated.resources.Res
import dishmatch.shared.generated.resources.compose_multiplatform

@Composable
@Preview

fun App() {
    MaterialTheme {
        var pantallaActual by remember { mutableStateOf("principal") }

        when (pantallaActual) {
            "principal" -> PaginaPrincipal(
                onIrCalendario = { pantallaActual = "calendario" },
                onIrMapa = { pantallaActual = "mapa" },
                onIrAjustes = { pantallaActual = "ajustes" } ,
                        onIrConocer = { pantallaActual = "conocer" },
                        onIrRestaurante = { pantallaActual = "restaurante" },
                        onIrPerfil = { pantallaActual = "perfil" }

            )
            "calendario" -> CalendarioScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
            "mapa" -> MapaScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
            "ajustes" -> AjustesScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
            "restaurante" -> RestauranteScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
            "perfil" -> PerfilScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
            "conocer" -> ConocerScreen(
                onVolverClick = { pantallaActual = "principal" }
            )
        }
    }
}