package com.pad.dishmatch.features.calendario.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.calendario.data.CalendarioRepositoryImpl
import com.pad.dishmatch.features.calendario.domain.CalendarioRepository
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