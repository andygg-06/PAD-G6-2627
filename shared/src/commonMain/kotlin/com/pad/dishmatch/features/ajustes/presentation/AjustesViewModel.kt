package com.pad.dishmatch.features.ajustes.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.ajustes.data.AjustesRepositoryImpl
import com.pad.dishmatch.features.ajustes.domain.Ajustes
import com.pad.dishmatch.features.ajustes.domain.AjustesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AjustesViewModel(
    private val repository: AjustesRepository = AjustesRepositoryImpl()
) : ViewModel() {

    private val _state = MutableStateFlow(
        AjustesState(ajustes = repository.obtenerAjustes())
    )
    val state = _state.asStateFlow()

    fun onAction(action: AjustesAction) {
        when (action) {
            is AjustesAction.CambiarTemaOscuro -> {
                actualizarAjustes { it.copy(temaOscuro = action.activado) }
            }

            is AjustesAction.CambiarNotificaciones -> {
                actualizarAjustes { it.copy(notificacionesHabilitadas = action.activadas) }
            }

            is AjustesAction.CambiarAvisosCitas -> {
                actualizarAjustes { it.copy(avisosCitas = action.activados) }
            }

            is AjustesAction.CambiarIdioma -> {
                actualizarAjustes { it.copy(idioma = action.idioma) }
            }

            is AjustesAction.CambiarRadioBusqueda -> {
                actualizarAjustes { it.copy(radioBusquedaKm = action.radioKm) }
            }

            is AjustesAction.RestablecerValores -> {
                val porDefecto = Ajustes()
                repository.guardarAjustes(porDefecto)
                _state.update { it.copy(ajustes = porDefecto) }
            }
        }
    }

    private fun actualizarAjustes(transformacion: (Ajustes) -> Ajustes) {
        _state.update { estadoActual ->
            val nuevosAjustes = transformacion(estadoActual.ajustes)
            repository.guardarAjustes(nuevosAjustes)
            estadoActual.copy(ajustes = nuevosAjustes)
        }
    }
}