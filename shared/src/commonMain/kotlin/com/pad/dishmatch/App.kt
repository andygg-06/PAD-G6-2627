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
import com.pad.dishmatch.features.conocer.data.ConocerRepositoryImpl
import com.pad.dishmatch.features.mapa.presentation.MapaScreenRoot
import com.pad.dishmatch.features.mapa.presentation.MapaViewModel
import com.pad.dishmatch.features.miperfil.data.PerfilRepositoryImpl
import com.pad.dishmatch.features.restaurantes.data.RestauranteRepositoryImpl
import com.pad.dishmatch.features.restaurantes.presentation.RestauranteScreen
import com.pad.dishmatch.features.restaurantes.presentation.RecomendacionPlatoScreen
import com.pad.dishmatch.features.restaurantes.presentation.CrearRestauranteScreen
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Restaurante

import com.pad.dishmatch.ui.home.PaginaPrincipal
import com.pad.dishmatch.ui.theme.TemaDishMatch

@Composable
@Preview
fun App(
    toScreen: String? = null,
    onProgramarRecordatorio: (titulo: String, mensaje: String) -> Unit = { _, _ -> },
    onPantallaAbierta: () -> Unit = {}
) {
    val ajustesViewModel = remember { AjustesViewModel() }
    val ajustesState by ajustesViewModel.state.collectAsStateWithLifecycle()

    TemaDishMatch(oscuro = ajustesState.ajustes.temaOscuro) {
        Surface(modifier = Modifier.fillMaxSize()) {

            val auth = remember { AuthRepositoryImpl() }
            var cuenta by remember { mutableStateOf(auth.cuentaActual()) }

            var pantallaActual by remember {
                mutableStateOf(if (cuenta == null) "login" else (toScreen ?: "principal"))
            }

            LaunchedEffect(toScreen) {
                if (cuenta != null) toScreen?.let {
                    pantallaActual = it
                    onPantallaAbierta()
                }
            }

            var perfil by remember { mutableStateOf(cuenta?.perfil ?: Perfil()) }

            val calendarioViewModel = remember { CalendarioViewModel() }
            val calendarioState by calendarioViewModel.state.collectAsStateWithLifecycle()
            val mapaViewModel = remember { MapaViewModel() }
            val conocerRepo = remember { ConocerRepositoryImpl() }
            val restauranteRepo = remember { RestauranteRepositoryImpl() }
            val perfilRepo = remember { PerfilRepositoryImpl(auth) }
            var personaElegida by remember { mutableStateOf<Perfil?>(null) }
            var restauranteElegido by remember { mutableStateOf<Restaurante?>(null) }
            var planPendiente by remember { mutableStateOf<Pair<Perfil, Restaurante>?>(null) }

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
                                pantallaActual = "perfil"
                                null
                            }
                            is ResultadoAuth.Fallo -> r.mensaje
                        }
                    },
                    onVolverLogin = { pantallaActual = "login" }
                )

                "principal" -> PaginaPrincipal(
                    proximaCita = calendarioState.todasLasCitas.firstOrNull(),
                    onIrCalendario = { pantallaActual = "calendario" },
                    onIrMapa = { pantallaActual = "mapa" },
                    onIrAjustes = { pantallaActual = "ajustes" },
                    onIrPerfil = { pantallaActual = "perfil" },
                    onIrConocer = { personaElegida = null; restauranteElegido = null; pantallaActual = "conocer" },
                    onIrRestaurante = { personaElegida = null; restauranteElegido = null; pantallaActual = "restaurante" }
                )

                "calendario" -> CalendarioScreenRoot(
                    viewModel = calendarioViewModel,
                    onVolverClick = {
                        planPendiente = null
                        pantallaActual = "principal"
                    },
                    textoPlanPendiente = planPendiente?.let { (p, r) ->
                        "Tu plan con ${p.nombre} en ${r.nom}"
                    },
                    onGuardarPlan = { dia, horaElegida ->
                        val plan = planPendiente
                        if (plan != null) {
                            calendarioViewModel.guardarPlan(
                                personaNombre = plan.first.nombre,
                                restauranteNombre = plan.second.nom,
                                ubicacion = plan.second.ubicacion,
                                dia = dia,
                                hora = horaElegida
                            )
                            if (ajustesState.ajustes.avisosCitas) {
                                onProgramarRecordatorio(
                                    "Tienes plan",
                                    "Has quedado con ${plan.first.nombre} en ${plan.second.nom} " +
                                        "el día $dia a las $horaElegida"
                                )
                            }
                            planPendiente = null
                            personaElegida = null
                            restauranteElegido = null
                        }
                    }
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
                    onGuardar = { perfil = it; perfilRepo.guardarPerfil(it) },

                    onVolverClick = { pantallaActual = "principal" },
                    onCerrarSesion = {
                        auth.cerrarSesion()
                        cuenta = null
                        perfil = Perfil()
                        personaElegida = null
                        restauranteElegido = null
                        planPendiente = null
                        pantallaActual = "login"
                    }
                )

                "restaurante" -> RestauranteScreen(
                    miPerfil = perfil,
                    persona = personaElegida,
                    repository = restauranteRepo,
                    onVolverClick = { personaElegida = null; pantallaActual = "principal" },
                    onCrearRestauranteClick = { pantallaActual = "crear_restaurante" },
                    onElegir = { r, luegoPersona ->
                        restauranteElegido = r
                        val p = personaElegida
                        if (p != null) {
                            pantallaActual = "recomendacion"
                        } else if (luegoPersona) {
                            pantallaActual = "conocer"
                        } else {
                            pantallaActual = "recomendacion"
                        }
                    }
                )

                "crear_restaurante" -> CrearRestauranteScreen(
                    onVolverClick = { pantallaActual = "restaurante" },
                    onGuardarRestaurante = { nuevo ->
                        restauranteRepo.agregarRestaurante(nuevo)
                        pantallaActual = "restaurante"
                    }
                )

                "conocer" -> ConocerScreen(
                    miPerfil = perfil,
                    restaurante = restauranteElegido,
                    repository = conocerRepo,
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
                            planPendiente = p to r
                            pantallaActual = "calendario"
                        } else {
                            pantallaActual = "principal"
                        }
                    }
                )
            }
        }
    }
}
