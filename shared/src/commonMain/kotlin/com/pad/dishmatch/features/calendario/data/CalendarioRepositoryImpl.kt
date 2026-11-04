package com.pad.dishmatch.features.calendario.data

import com.pad.dishmatch.features.calendario.domain.CalendarioRepository
import com.pad.dishmatch.features.calendario.domain.Cita

class CalendarioRepositoryImpl : CalendarioRepository {
    private val citas = mutableListOf(
        Cita(
            id = "1",
            personaNombre = "Carlos V.",
            restauranteNombre = "El Bistro de Pedro",
            ubicacion = "Madrid - Centro",
            fecha = "14 de Noviembre",
            hora = "20:30",
            diaMes = 14
        ),
        Cita(
            id = "2",
            personaNombre = "Ana R.",
            restauranteNombre = "Taberna La Chulapa",
            ubicacion = "Madrid - Centro",
            fecha = "21 de Noviembre",
            hora = "21:00",
            diaMes = 21
        ),
        Cita(
            id = "3",
            personaNombre = "Elena M.",
            restauranteNombre = "Wok & Roll",
            ubicacion = "Madrid - Malasaña",
            fecha = "28 de Noviembre",
            hora = "14:30",
            diaMes = 28
        )
    )

    override fun getCitas(): List<Cita> = citas.toList()

    override fun cancelarCita(id: String): List<Cita> {
        citas.removeAll { it.id == id }
        return citas.toList()
    }
}