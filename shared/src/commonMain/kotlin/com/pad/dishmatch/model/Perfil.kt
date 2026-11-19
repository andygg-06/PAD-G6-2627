package com.pad.dishmatch.model
import kotlinx.serialization.Serializable

/* MODELO PRINCIPAL DEL USUARIO:
    - Estructura base
    - Información del usuario
    - Preferencias gastronómicas
    - Salud del usuario: dietas, alérgenos ...
    - Tipos enumerados para cada una de las opciones posibles
    - Ajustes de perfil
 */

@Serializable
data class Perfil(
    val id: String = "",
    val nombre: String = "",
    val edad: Int = 0,
    val biografia: String = "",
    val foto: String? = null,
    val dieta: Set<AdjPlato> = emptySet<AdjPlato>(),
    val personalidad: Set<AdjPersonas> = emptySet<AdjPersonas>(),
    val busco: Set<AdjPersonas> = emptySet<AdjPersonas>(),
    val estilosRestaurante: Set<AdjRestaurante> = emptySet<AdjRestaurante>()
)