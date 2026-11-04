package com.pad.dishmatch.features.conocer.presentation

import com.pad.dishmatch.features.conocer.domain.Comensal
import com.pad.dishmatch.model.AdjPlato

data class ConocerState(
    val candidatos: List<Comensal> = emptyList(),
    val indiceActual: Int = 0,
    val filtroDieta: AdjPlato? = null,
    val mensajeFeedback: String? = null
) {
    // Propiedad calculada: el comensal visible ahora mismo (o null si ya los viste todos)
    val comensalActual: Comensal?
        get() = candidatos.getOrNull(indiceActual)
}