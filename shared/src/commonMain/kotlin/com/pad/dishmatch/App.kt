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
import org.jetbrains.compose.resources.painterResource

import dishmatch.shared.generated.resources.Res
import dishmatch.shared.generated.resources.compose_multiplatform
import com.pad.dishmatch.model.Perfil
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import com.pad.dishmatch.features.restaurantes.presentation.RestaurantesScreenRoot
import com.pad.dishmatch.features.restaurantes.presentation.RestaurantesViewModel
import com.pad.dishmatch.features.conocer.presentation.ConocerScreenRoot
import com.pad.dishmatch.features.conocer.presentation.ConocerViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        var pantallaActual by remember { mutableStateOf("principal") }
        var perfil by remember { mutableStateOf(Perfil()) }
        val restaurantesViewModel = remember { RestaurantesViewModel() }
        val conocerViewModel = remember { ConocerViewModel() }

        when (pantallaActual) {
            "principal" -> PaginaPrincipal(
                onIrCalendario = { pantallaActual = "calendario" },
                onIrMapa = { pantallaActual = "mapa" },
                onIrAjustes = { pantallaActual = "ajustes" },
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
            "restaurante" -> RestaurantesScreenRoot(
                viewModel = restaurantesViewModel,
                onVolverClick = { pantallaActual = "principal" }
            )
            "perfil" -> PerfilScreen(
                perfil = perfil,
                onGuardar = { perfil = it },
                onVolverClick = { pantallaActual = "principal" }
            )
            "conocer" -> ConocerScreenRoot(
                viewModel = conocerViewModel,
                onVolverClick = { pantallaActual = "principal" }
            )
        }
    }
}