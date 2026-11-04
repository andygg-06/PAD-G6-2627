package com.pad.dishmatch.features.mapa.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.mapa.data.MapaRepositoryImpl
import com.pad.dishmatch.features.mapa.domain.MapaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapaViewModel(
    private val repository: MapaRepository = MapaRepositoryImpl()
) : ViewModel() {

    private val _state = MutableStateFlow(
        MapaState(
            puntos = repository.obtenerPuntos(),
            puntoSeleccionado = repository.obtenerPuntos().firstOrNull()
        )
    )
    val state = _state.asStateFlow()

    fun onAction(action: MapaAction) {
        when (action) {
            is MapaAction.SeleccionarPunto -> {
                _state.update { estadoActual ->
                    estadoActual.copy(puntoSeleccionado = action.punto)
                }
            }

            is MapaAction.DeseleccionarPunto -> {
                _state.update { estadoActual ->
                    estadoActual.copy(puntoSeleccionado = null)
                }
            }

            is MapaAction.CambiarFiltroDistancia -> {
                val filtrados = repository.filtrarPorDistancia(action.distanciaMetros)
                _state.update { estadoActual ->
                    estadoActual.copy(
                        filtroDistanciaMax = action.distanciaMetros,
                        puntos = filtrados,
                        puntoSeleccionado = filtrados.firstOrNull()
                    )
                }
            }
        }
    }
}