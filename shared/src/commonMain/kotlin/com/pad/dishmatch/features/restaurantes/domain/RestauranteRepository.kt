package com.pad.dishmatch.features.restaurantes.domain

import com.pad.dishmatch.model.Restaurante

interface RestauranteRepository {

    //Devuelve la lista completa de restaurantes disponibles.

    fun obtenerRestaurantes(): List<Restaurante>

    //Busca un restaurante concreto por su nombre.

    fun obtenerRestaurantePorNombre(nombre: String): Restaurante?
}