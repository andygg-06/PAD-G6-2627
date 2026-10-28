package com.pad.dishmatch.model
import AdjPersonas
import AdjPlato
import AdjRestaurante
import kotlinx.serialization.Serializable


@Serializable
data class Perfil(
    val nombre: String = "",
    val biografia: String = "",
    val foto: String? = null,
    val dieta: Set<AdjPlato> = emptySet<AdjPlato>(),
    val personalidad: Set<AdjPersonas> = emptySet<AdjPersonas>(),
    val busco: Set<AdjPersonas> = emptySet<AdjPersonas>(),
    val estilosRestaurante: Set<AdjRestaurante> = emptySet<AdjRestaurante>()
)