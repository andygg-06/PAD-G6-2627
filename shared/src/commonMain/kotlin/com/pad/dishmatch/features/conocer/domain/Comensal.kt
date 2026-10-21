package com.pad.dishmatch.features.conocer.domain

import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato

//Representa a un usuario afín con el que coincidir para comer.

data class Comensal(
    val id: String,
    val nombre: String,
    val edad: Int,
    val facultad: String,
    val bio: String,
    val avatarEmoji: String,
    val personalidad: Array<AdjPersonas> = emptyArray(),
    val dietasYGustos: Array<AdjPlato> = emptyArray()
)