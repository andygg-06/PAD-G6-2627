package com.pad.dishmatch.features.calendario.presentation

import com.pad.dishmatch.features.calendario.domain.Cita

data class CalendarioState(
    val diaSeleccionado: Int = 14,
    val todasLasCitas: List<Cita> = emptyList(),
    val citasDelDia: List<Cita> = emptyList(),
    val isLoading: Boolean = false
)