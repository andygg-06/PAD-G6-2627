package com.pad.dishmatch.features.restaurantes.presentation

import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Restaurante

//Representa la "foto fija" inmutable de todo lo que la pantalla de restaurantes puede mostrar.

data class RestaurantesState(
    val restaurantes: List<Restaurante> = emptyList(),
    val textoBusqueda: String = "",
    val filtroEstilo: AdjRestaurante? = null,
    val filtroDieta: AdjPlato? = null,
    val restauranteSeleccionado: Restaurante? = null
)