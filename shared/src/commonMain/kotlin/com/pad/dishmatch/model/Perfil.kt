package com.pad.dishmatch.model
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjRestaurante
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