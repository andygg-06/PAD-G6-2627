package com.pad.dishmatch.features.conocer.domain

interface ConocerRepository {

    //Devuelve la lista de personas afines disponibles para hacer Match.

    fun obtenerCandidatos(): List<Comensal>

    //Registra una propuesta de quedada gastronómica con esa persona.

    fun proponerQuedada(comensalId: String, mensaje: String): Boolean
}