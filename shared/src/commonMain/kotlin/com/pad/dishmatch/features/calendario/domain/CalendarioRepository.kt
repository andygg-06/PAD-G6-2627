package com.pad.dishmatch.features.calendario.domain

interface CalendarioRepository {
    fun getCitas(): List<Cita>
    fun cancelarCita(id: String): List<Cita>
    fun agregarCita(cita: Cita): List<Cita>
}
