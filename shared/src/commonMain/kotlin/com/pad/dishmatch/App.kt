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
import com.pad.dishmatch.model.Restaurante

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf


import com.pad.dishmatch.features.calendario.presentation.CalendarioScreenRoot
import com.pad.dishmatch.features.calendario.presentation.CalendarioViewModel

import com.pad.dishmatch.features.mapa.presentation.MapaScreenRoot
import com.pad.dishmatch.features.mapa.presentation.MapaViewModel

import com.pad.dishmatch.features.ajustes.presentation.AjustesScreenRoot
import com.pad.dishmatch.features.ajustes.presentation.AjustesViewModel
import com.pad.dishmatch.features.restaurantes.presentation.RestauranteScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        var pantallaActual by remember { mutableStateOf("principal") }
        var perfil by remember { mutableStateOf(Perfil()) }
        val calendarioViewModel = remember { CalendarioViewModel() }
        val mapaViewModel = remember { MapaViewModel() }
        val ajustesViewModel = remember { AjustesViewModel() }
        //variables que nos serviran para pasar entre pantallas
        var personaElegida by remember { mutableStateOf<Perfil?>(null) }
        var restauranteElegido by remember { mutableStateOf<Restaurante?>(null) }

        when (pantallaActual) {
            "principal" -> PaginaPrincipal(
                onIrCalendario = { pantallaActual = "calendario" },
                onIrMapa = { pantallaActual = "mapa" },
                onIrAjustes = { pantallaActual = "ajustes" },
                onIrConocer = { pantallaActual = "conocer" },
                onIrRestaurante = { pantallaActual = "restaurante" },
                onIrPerfil = { pantallaActual = "perfil" }
            )
            "calendario" -> CalendarioScreenRoot(
                viewModel = calendarioViewModel,
                onVolverClick = { pantallaActual = "principal" }
            )
            "mapa" -> MapaScreenRoot(
                viewModel = mapaViewModel,
                onVolverClick = { pantallaActual = "principal" }
            )
            "ajustes" -> AjustesScreenRoot(
                viewModel = ajustesViewModel,
                onVolverClick = { pantallaActual = "principal" }
            )

            "restaurante" -> RestauranteScreen(
                miPerfil = perfil,
                persona = personaElegida,
                onVolverClick = { personaElegida = null; pantallaActual = "principal" },
                onElegir = { restauranteElegido = it; pantallaActual = "principal" }
            )
            "perfil" -> PerfilScreen(
                perfil = perfil,
                onGuardar = { perfil = it },
                onVolverClick = { pantallaActual = "principal" }
            )

            "conocer" -> ConocerScreen(
                miPerfil = perfil,
                onVolverClick = { pantallaActual = "principal" },
                onQuedar = { personaElegida = it; pantallaActual = "restaurante" }
            )
        }
    }
}
