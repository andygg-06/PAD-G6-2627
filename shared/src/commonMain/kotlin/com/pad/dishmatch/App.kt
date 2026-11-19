package com.pad.dishmatch

import ConocerScreen
import PerfilScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pad.dishmatch.features.ajustes.presentation.AjustesScreenRoot
import com.pad.dishmatch.features.ajustes.presentation.AjustesViewModel
import com.pad.dishmatch.features.calendario.presentation.CalendarioScreenRoot
import com.pad.dishmatch.features.calendario.presentation.CalendarioViewModel
import com.pad.dishmatch.features.mapa.presentation.MapaScreenRoot
import com.pad.dishmatch.features.mapa.presentation.MapaViewModel
import com.pad.dishmatch.features.restaurantes.presentation.RestauranteScreen
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Restaurante
import com.pad.dishmatch.model.Cita

import com.pad.dishmatch.ui.home.PaginaPrincipal
import com.pad.dishmatch.ui.theme.TemaDishMatch

@Composable
@Preview
fun App(toScreen: String? = null) {
    // El tema depende de los ajustes
    val ajustesViewModel = remember { AjustesViewModel() }
    val ajustesState by ajustesViewModel.state.collectAsStateWithLifecycle()

    TemaDishMatch(oscuro = ajustesState.ajustes.temaOscuro) {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Si venimos de una notificación, empezamos en esa pantalla
            var pantallaActual by remember { mutableStateOf(toScreen ?: "principal") }

            // Si la app ya estaba abierta, la notificación cambia la pantalla aquí
            LaunchedEffect(toScreen) {
                toScreen?.let { pantallaActual = it }
            }
            var perfil by remember { mutableStateOf(PerfilStorage.cargar()) }
            //var perfil by remember { mutableStateOf(Perfil()) } antiguo, ahora para que se guarde
            val calendarioViewModel = remember { CalendarioViewModel() }
            val mapaViewModel = remember { MapaViewModel() }

            //variables para pasar datos entre pantallas a la hora de pasar de conocer
            //a restaurante y al reves
            var personaElegida by remember { mutableStateOf<Perfil?>(null) }
            var restauranteElegido by remember { mutableStateOf<Restaurante?>(null) }
            var citas by remember { mutableStateOf(listOf<Cita>()) }
            when (pantallaActual) {
                "principal" -> PaginaPrincipal(
                    onIrCalendario = { pantallaActual = "calendario" },
                    onIrMapa = { pantallaActual = "mapa" },
                    onIrAjustes = { pantallaActual = "ajustes" },
                    onIrPerfil = { pantallaActual = "perfil" },
                    //se pone a null para no arrastrar la cita anterior
                    onIrConocer = { personaElegida = null; restauranteElegido = null; pantallaActual = "conocer" },
                    onIrRestaurante = { personaElegida = null; restauranteElegido = null; pantallaActual = "restaurante" }
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



                "perfil" -> PerfilScreen(
                    perfil = perfil,
                    onGuardar = { perfil = it; PerfilStorage.guardar(it) },
                    onVolverClick = { pantallaActual = "principal" }
                )

                "restaurante" -> RestauranteScreen(
                    miPerfil = perfil,
                    persona = personaElegida,
                    onVolverClick = { personaElegida = null; pantallaActual = "principal" },
                    onElegir = { r, luegoPersona ->
                        val p = personaElegida
                        if (p != null) {
                            // Flujo persona -> restaurante: la cita queda hecha
                            citas = citas + Cita(p, r)
                            personaElegida = null
                            pantallaActual = "principal"
                        } else if (luegoPersona) {
                            // Flujo restaurante -> persona
                            restauranteElegido = r
                            pantallaActual = "conocer"
                        } else {
                            pantallaActual = "principal"
                        }
                    }
                )

                "conocer" -> ConocerScreen(
                    miPerfil = perfil,
                    restaurante = restauranteElegido,
                    onVolverClick = { restauranteElegido = null; pantallaActual = "principal" },
                    onQuedar = { p ->
                        val r = restauranteElegido
                        if (r != null) {
                            citas = citas + Cita(p, r)
                            restauranteElegido = null
                            pantallaActual = "principal"
                        } else {
                            personaElegida = p
                            pantallaActual = "restaurante"
                        }
                    }
                )
            }
        }
    }
}