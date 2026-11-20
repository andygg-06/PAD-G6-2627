package com.pad.dishmatch.features.restaurantes.domain

import com.pad.dishmatch.model.Restaurante

//la pantalla solo conoce esta interfaz.
interface RestauranteRepository {
    fun obtenerRestaurantes(): List<Restaurante>
}