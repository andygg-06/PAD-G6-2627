package com.pad.dishmatch.features.calendario.data

import com.pad.dishmatch.features.calendario.domain.CalendarioRepository
import com.pad.dishmatch.features.calendario.domain.Cita

class CalendarioRepositoryImpl : CalendarioRepository {

    override fun getCitas(): List<Cita> = citas.toList()

    override fun cancelarCita(id: String): List<Cita> {
        citas.removeAll { it.id == id }
        return citas.toList()
    }

    override fun agregarCita(cita: Cita): List<Cita> {
        citas.add(cita)
        return citas.toList()
    }

    // La lista vive en el companion object: todas las instancias del repositorio
    // ven las mismas citas, asi el plan que guardas sigue ahi al volver a entrar
    companion object {
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
    }
}
