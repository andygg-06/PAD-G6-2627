package com.pad.dishmatch.features.ajustes.domain

data class Ajustes(
    val temaOscuro: Boolean = false,
    val notificacionesHabilitadas: Boolean = true,
    val idioma: String = "Español",
    val radioBusquedaKm: Float = 5.0f,
    val avisosCitas: Boolean = true
)