package com.pad.dishmatch.features.calendario.presentation

sealed interface CalendarioAction {
    data class SeleccionarDia(val dia: Int) : CalendarioAction
    data class CancelarCita(val id: String) : CalendarioAction
}