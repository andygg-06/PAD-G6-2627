package com.pad.dishmatch.features.mapa.presentation

import com.pad.dishmatch.features.mapa.domain.PuntoMapa

data class MapaState(
    val puntos: List<PuntoMapa> = emptyList(),
    val puntoSeleccionado: PuntoMapa? = null,
    val filtroDistanciaMax: Int = 2000, // En metros (2 km por defecto)
    val isLoading: Boolean = false
)