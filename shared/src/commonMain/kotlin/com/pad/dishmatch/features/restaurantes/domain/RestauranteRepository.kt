package com.pad.dishmatch.features.restaurantes.domain

import com.pad.dishmatch.model.Restaurante

interface RestauranteRepository {
    fun obtenerRestaurantes(): List<Restaurante>
    fun agregarRestaurante(restaurante: Restaurante)
}
