package com.pad.dishmatch.features.mapa.data

import com.pad.dishmatch.features.mapa.domain.MapaRepository
import com.pad.dishmatch.features.mapa.domain.PuntoMapa
import com.pad.dishmatch.features.mapa.domain.TipoPunto

class MapaRepositoryImpl : MapaRepository {

    private val puntos = listOf(
        PuntoMapa(
            id = "1",
            nombre = "El Bistro de Pedro",
            descripcion = "Cocina de mercado en ambiente acogedor",
            tipo = TipoPunto.RESTAURANTE,
            direccion = "Calle del Pez, 14 (Malasaña)",
            distanciaMetros = 350,
            latitud = 40.4235,
            longitud = -3.7058,
            precio = "€€",
            valoracion = 4.7
        ),
        PuntoMapa(
            id = "2",
            nombre = "Taberna La Chulapa",
            descripcion = "Tapas tradicionales y vinos locales",
            tipo = TipoPunto.RESTAURANTE,
            direccion = "Plaza Mayor, 8 (Centro)",
            distanciaMetros = 650,
            latitud = 40.4155,
            longitud = -3.7074,
            precio = "€",
            valoracion = 4.5
        ),
        PuntoMapa(
            id = "3",
            nombre = "Wok & Roll",
            descripcion = "Fusión asiática y platos rápidos",
            tipo = TipoPunto.RESTAURANTE,
            direccion = "Calle Fuencarral, 45 (Chueca)",
            distanciaMetros = 900,
            latitud = 40.4218,
            longitud = -3.7012,
            precio = "€€",
            valoracion = 4.2
        ),
        PuntoMapa(
            id = "4",
            nombre = "Cita con Carlos V.",
            descripcion = "Quedada confirmada para cenar",
            tipo = TipoPunto.CITA,
            direccion = "Puerta del Sol (Kilómetro Cero)",
            distanciaMetros = 450,
            latitud = 40.4168,
            longitud = -3.7038,
            precio = "€€",
            valoracion = 5.0
        ),
        PuntoMapa(
            id = "5",
            nombre = "Cita con Ana R.",
            descripcion = "Quedada confirmada para tomar algo",
            tipo = TipoPunto.CITA,
            direccion = "Plaza del Dos de Mayo",
            distanciaMetros = 1200,
            latitud = 40.4270,
            longitud = -3.7040,
            precio = "€",
            valoracion = 5.0
        )
    )

    override fun obtenerPuntos(): List<PuntoMapa> = puntos

    override fun filtrarPorDistancia(maxMetros: Int): List<PuntoMapa> {
        return puntos.filter { it.distanciaMetros <= maxMetros }
    }
}