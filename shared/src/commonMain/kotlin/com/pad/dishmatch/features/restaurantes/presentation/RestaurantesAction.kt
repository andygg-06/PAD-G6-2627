package com.pad.dishmatch.features.restaurantes.presentation

import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Restaurante

//Catálogo completo de todas las interacciones posibles del usuario en la pantalla.

sealed interface RestaurantesAction {
    data class CambiarTextoBusqueda(val nuevoTexto: String) : RestaurantesAction
    data class SeleccionarFiltroEstilo(val estilo: AdjRestaurante?) : RestaurantesAction
    data class SeleccionarFiltroDieta(val dieta: AdjPlato?) : RestaurantesAction
    data class ClickRestaurante(val restaurante: Restaurante) : RestaurantesAction
    data object CerrarDetalleRestaurante : RestaurantesAction
    data object VolverAPrincipal : RestaurantesAction
}