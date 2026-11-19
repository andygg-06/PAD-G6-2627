package com.pad.dishmatch.features.calendario.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.calendario.data.CalendarioRepositoryImpl
import com.pad.dishmatch.features.calendario.domain.CalendarioRepository
import com.pad.dishmatch.features.calendario.domain.Cita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalendarioViewModel(
    private val repository: CalendarioRepository = CalendarioRepositoryImpl()
) : ViewModel() {

    private val todasLasCitas = repository.getCitas()

    private val _state = MutableStateFlow(
        CalendarioState(
            diaSeleccionado = 14,
            todasLasCitas = todasLasCitas,
            citasDelDia = todasLasCitas.filter { it.diaMes == 14 }
        )
    )
    val state = _state.asStateFlow()

    /** Vuelve a leer del repositorio: lo llama la pantalla cada vez que se abre */
    fun refrescar() {
        val actualizadas = repository.getCitas()
        _state.update { estado ->
            estado.copy(
                todasLasCitas = actualizadas,
                citasDelDia = actualizadas.filter { it.diaMes == estado.diaSeleccionado }
            )
        }
    }

    /** Guarda en el calendario el plan que el usuario acaba de confirmar */
    fun guardarPlan(
        personaNombre: String,
        restauranteNombre: String,
        ubicacion: String,
        dia: Int,
        hora: String
    ) {
        val nueva = Cita(
            id = "plan-$dia-$hora-$personaNombre",
            personaNombre = personaNombre,
            restauranteNombre = restauranteNombre,
            ubicacion = ubicacion,
            fecha = "$dia de Noviembre",
            hora = hora,
            diaMes = dia
        )
        val actualizadas = repository.agregarCita(nueva)
        _state.update { estado ->
            estado.copy(
                diaSeleccionado = dia,
                todasLasCitas = actualizadas,
                citasDelDia = actualizadas.filter { it.diaMes == dia }
            )
        }
    }

    fun onAction(action: CalendarioAction) {
        when (action) {
            is CalendarioAction.SeleccionarDia -> {
                _state.update { estadoActual ->
                    estadoActual.copy(
                        diaSeleccionado = action.dia,
                        citasDelDia = estadoActual.todasLasCitas.filter { it.diaMes == action.dia }
                    )
                }
            }

            is CalendarioAction.CancelarCita -> {
                val citasActualizadas = repository.cancelarCita(action.id)
                _state.update { estadoActual ->
                    estadoActual.copy(
                        todasLasCitas = citasActualizadas,
                        citasDelDia = citasActualizadas.filter { it.diaMes == estadoActual.diaSeleccionado }
                    )
                }
            }
        }
    }
}
