package com.pad.dishmatch.features.ajustes.presentation

sealed interface AjustesAction {
    data class CambiarTemaOscuro(val activado: Boolean) : AjustesAction
    data class CambiarNotificaciones(val activadas: Boolean) : AjustesAction
    data class CambiarAvisosCitas(val activados: Boolean) : AjustesAction
    data class CambiarIdioma(val idioma: String) : AjustesAction
    data class CambiarRadioBusqueda(val radioKm: Float) : AjustesAction
    data object RestablecerValores : AjustesAction
}