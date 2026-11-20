package com.pad.dishmatch.features.conocer.data

import com.pad.dishmatch.features.conocer.domain.ConocerRepository
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Perfil

class ConocerRepositoryImpl : ConocerRepository {

    private val candidatos = listOf(
        Perfil(
            id = "c1", nombre = "Laura M.", edad = 21,
            biografia = "Fanática del ramen y la comida asiática.",
            personalidad = setOf(AdjPersonas.FIESTERO, AdjPersonas.ROMANTICO),
            dieta = setOf(AdjPlato.VEGETARIANO, AdjPlato.SALUDABLE),
            estilosRestaurante = setOf(AdjRestaurante.URBANO, AdjRestaurante.ELEGANTE)
        ),
        Perfil(
            id = "c2", nombre = "Carlos G.", edad = 23,
            biografia = "Busco sitios con platos altos en proteína.",
            personalidad = setOf(AdjPersonas.ALTO),
            dieta = setOf(AdjPlato.PROTEINA, AdjPlato.INTOLERANTE_LACTOSA),
            estilosRestaurante = setOf(AdjRestaurante.URBANO, AdjRestaurante.TRADICIONAL)
        ),
        Perfil(
            id = "c3", nombre = "Elena R.", edad = 20,
            biografia = "Vegana estricta, busco cafeterías chulas.",
            personalidad = setOf(AdjPersonas.ROMANTICO),
            dieta = setOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE),
            estilosRestaurante = setOf(AdjRestaurante.ELEGANTE)
        )
    )

    override fun obtenerCandidatos(): List<Perfil> = candidatos

    override fun proponerQuedada(personaId: String): Boolean {
        //envia la propuesta al servidor
        return true
    }
}