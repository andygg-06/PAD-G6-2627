package com.pad.dishmatch.features.conocer.domain

import com.pad.dishmatch.model.Perfil

interface ConocerRepository {
    //personas disponibles para quedar
    fun obtenerCandidatos(): List<Perfil>
    //guarda una propuesta de quedada con esa persona
    fun proponerQuedada(personaId: String): Boolean
}