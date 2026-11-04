package com.pad.dishmatch.features.calendario.domain

data class Cita(
    val id: String,
    val personaNombre: String,
    val restauranteNombre: String,
    val ubicacion: String,
    val fecha: String,
    val hora: String,
    val diaMes: Int,
    val estado: String = "Confirmada"
)