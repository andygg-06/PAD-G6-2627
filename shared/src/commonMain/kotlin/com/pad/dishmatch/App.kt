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
import com.pad.dishmatch.features.autentificacion.data.AuthRepositoryImpl
import com.pad.dishmatch.features.autentificacion.domain.ResultadoAuth
import com.pad.dishmatch.features.autentificacion.presentation.LoginScreen
import com.pad.dishmatch.features.autentificacion.presentation.RegistroScreen
import com.pad.dishmatch.features.calendario.presentation.CalendarioScreenRoot
import com.pad.dishmatch.features.calendario.presentation.CalendarioViewModel
import com.pad.dishmatch.features.mapa.presentation.MapaScreenRoot
import com.pad.dishmatch.features.mapa.presentation.MapaViewModel
import com.pad.dishmatch.features.restaurantes.presentation.RestauranteScreen
import com.pad.dishmatch.features.restaurantes.presentation.RecomendacionPlatoScreen
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

            // Autentificacion del usuario
            val auth = remember { AuthRepositoryImpl() }
            var cuenta by remember { mutableStateOf(auth.cuentaActual()) }

            // Sin sesión empezamos en login; con sesion, en la pantalla de la notificacion o en principal
            var pantallaActual by remember {
                mutableStateOf(if (cuenta == null) "login" else (toScreen ?: "principal"))
            }

            // Si la app ya estaba abierta, la notificacion cambia la pantalla (solo con sesión iniciada)
            LaunchedEffect(toScreen) {
                if (cuenta != null) toScreen?.let { pantallaActual = it }
            }

            // El perfil cuelga de la cuenta (ya no se usa PerfilStorage)
            var perfil by remember { mutableStateOf(cuenta?.perfil ?: Perfil()) }

            val calendarioViewModel = remember { CalendarioViewModel() }
            val mapaViewModel = remember { MapaViewModel() }

            //variables para pasar datos entre pantallas a la hora de pasar de conocer
            //a restaurante y al reves
            var personaElegida by remember { mutableStateOf<Perfil?>(null) }
            var restauranteElegido by remember { mutableStateOf<Restaurante?>(null) }
            var citas by remember { mutableStateOf(listOf<Cita>()) }

            when (pantallaActual) {
                "login" -> LoginScreen(
                    onLogin = { email, pass ->
                        when (val r = auth.iniciarSesion(email, pass)) {
                            is ResultadoAuth.Exito -> {
                                cuenta = r.cuenta
                                perfil = r.cuenta.perfil
                                pantallaActual = "principal"
                                null
                            }
                            is ResultadoAuth.Fallo -> r.mensaje
                        }
                    },
                    onIrRegistro = { pantallaActual = "registro" }
                )

                "registro" -> RegistroScreen(
                    onRegistrar = { nombre, email, pass, consentimientoSalud ->
                        when (val r = auth.registrar(email, pass, Perfil(nombre = nombre), consentimientoSalud)) {
                            is ResultadoAuth.Exito -> {
                                cuenta = r.cuenta
                                perfil = r.cuenta.perfil
                                pantallaActual = "perfil" // completar preferencias
                                null
                            }
                            is ResultadoAuth.Fallo -> r.mensaje
                        }
                    },
                    onVolverLogin = { pantallaActual = "login" }
                )

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
                    onGuardar = { perfil = it; auth.actualizarPerfil(it) },
                    onVolverClick = { pantallaActual = "principal" }
                )

                "restaurante" -> RestauranteScreen(
                    miPerfil = perfil,
                    persona = personaElegida,
                    onVolverClick = { personaElegida = null; pantallaActual = "principal" },
                    onElegir = { r, luegoPersona ->
                        restauranteElegido = r
                        val p = personaElegida
                        if (p != null) {
                            pantallaActual = "recomendacion"
                        } else if (luegoPersona) {
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
                        personaElegida = p
                        val r = restauranteElegido
                        if (r != null) {
                            pantallaActual = "recomendacion"
                        } else {
                            pantallaActual = "restaurante"
                        }
                    }
                )

                "recomendacion" -> RecomendacionPlatoScreen(
                    miPerfil = perfil,
                    persona = personaElegida,
                    restaurante = restauranteElegido,
                    onVolverClick = {
                        pantallaActual = "principal"
                    },
                    onConfirmarPlan = {
                        val p = personaElegida
                        val r = restauranteElegido
                        if (p != null && r != null) {
                            citas = citas + Cita(p, r)
                        }
                        personaElegida = null
                        restauranteElegido = null
                        pantallaActual = "principal"
                    }
                )
            }
        }
    }
}