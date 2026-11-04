package com.pad.dishmatch.features.mapa.domain

enum class TipoPunto {
    RESTAURANTE,
    CITA
}

data class PuntoMapa(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val tipo: TipoPunto,
    val direccion: String,
    val distanciaMetros: Int,
    val latitud: Double,
    val longitud: Double,
    val precio: String = "€€",
    val valoracion: Double = 4.5
)