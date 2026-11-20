package com.pad.dishmatch.features.restaurantes.domain

import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.Plato
import com.pad.dishmatch.model.Restaurante

//logica de negocio
//devuelve el plato de la carta con más etiquetas en común con la dieta
fun recomendarPlato(restaurante: Restaurante, dieta: Set<AdjPlato>): Plato? {
    if (restaurante.carta.isEmpty()) return null
    if (dieta.isEmpty()) return restaurante.carta.first()
    return restaurante.carta.maxByOrNull { plato ->
        plato.tipo.count { it in dieta }   //cuantas etiquetas coinciden
    }
}