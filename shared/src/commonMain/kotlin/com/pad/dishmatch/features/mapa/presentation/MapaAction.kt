package com.pad.dishmatch.features.mapa.presentation

import com.pad.dishmatch.features.mapa.domain.PuntoMapa

sealed interface MapaAction {
    data class SeleccionarPunto(val punto: PuntoMapa) : MapaAction
    data object DeseleccionarPunto : MapaAction
    data class CambiarFiltroDistancia(val distanciaMetros: Int) : MapaAction
}