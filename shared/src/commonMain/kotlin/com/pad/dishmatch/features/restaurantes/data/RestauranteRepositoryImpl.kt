package com.pad.dishmatch.features.restaurantes.data

import com.pad.dishmatch.features.restaurantes.domain.RestauranteRepository
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Plato
import com.pad.dishmatch.model.Restaurante

class RestauranteRepositoryImpl : RestauranteRepository {

    private val restaurantes = mutableListOf(
        Restaurante(
            nom = "La Cantina Universitaria",
            ubicacion = "Ciudad Universitaria (Moncloa)",
            precioMin = 8,
            precioMax = 14,
            estilosRestaurante = arrayOf(AdjRestaurante.URBANO, AdjRestaurante.TRADICIONAL),
            carta = arrayOf(
                Plato("Ensalada Completa de Quinoa", 8, arrayOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE)),
                Plato("Pechuga Asada con Arroz Integral", 10, arrayOf(AdjPlato.PROTEINA, AdjPlato.SALUDABLE)),
                Plato("Tortilla Española Casera", 7, arrayOf(AdjPlato.VEGETARIANO))
            )
        ),
        Restaurante(
            nom = "Verde & Sano Poke Bar",
            ubicacion = "Calle Princesa, 24",
            precioMin = 10,
            precioMax = 18,
            estilosRestaurante = arrayOf(AdjRestaurante.URBANO),
            carta = arrayOf(
                Plato("Poke Bowl Salmón Fresco", 13, arrayOf(AdjPlato.PROTEINA, AdjPlato.ALERGIA_PESCADO)),
                Plato("Bowl Tofu y Aguacate", 11, arrayOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE)),
                Plato("Smoothie Bowl Proteico", 7, arrayOf(AdjPlato.PROTEINA, AdjPlato.INTOLERANTE_LACTOSA))
            )
        ),
        Restaurante(
            nom = "Trattoria Bella Vista",
            ubicacion = "Plaza de España, 5",
            precioMin = 15,
            precioMax = 25,
            estilosRestaurante = arrayOf(AdjRestaurante.ELEGANTE, AdjRestaurante.TRADICIONAL),
            carta = arrayOf(
                Plato("Pizza Margherita al Horno", 12, arrayOf(AdjPlato.VEGETARIANO, AdjPlato.INTOLERANTE_LACTOSA)),
                Plato("Pasta Carbonara Tradicional", 14, arrayOf(AdjPlato.INTOLERANTE_LACTOSA)),
                Plato("Tiramisú Casero", 6, arrayOf(AdjPlato.INTOLERANTE_LACTOSA))
            )
        )
    )

    override fun obtenerRestaurantes(): List<Restaurante> = restaurantes.toList()

    override fun agregarRestaurante(restaurante: Restaurante) {
        restaurantes.add(restaurante)
    }
}
