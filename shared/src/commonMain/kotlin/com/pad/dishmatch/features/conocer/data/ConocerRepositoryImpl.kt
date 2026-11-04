package com.pad.dishmatch.features.conocer.data

import com.pad.dishmatch.features.conocer.domain.Comensal
import com.pad.dishmatch.features.conocer.domain.ConocerRepository
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato

class ConocerRepositoryImpl : ConocerRepository {

    private val candidatos = listOf(
        Comensal(
            id = "c1",
            nombre = "Laura M.",
            edad = 21,
            facultad = "Facultad de Informática (UCM)",
            bio = "Fanática del ramen y la comida asiática. Busco gente para ir a probar sitios nuevos cerca de Moncloa.",
            avatarEmoji = "👩‍💻",
            personalidad = arrayOf(AdjPersonas.FIESTERO, AdjPersonas.ROMANTICO),
            dietasYGustos = arrayOf(AdjPlato.VEGETARIANO, AdjPlato.SALUDABLE)
        ),
        Comensal(
            id = "c2",
            nombre = "Carlos G.",
            edad = 23,
            facultad = "Medicina",
            bio = "Entreno casi todos los días. Necesito sitios donde sirvan platos altos en proteína y comida sin lactosa.",
            avatarEmoji = "🏋️‍♂️",
            personalidad = arrayOf(AdjPersonas.ALTO),
            dietasYGustos = arrayOf(AdjPlato.PROTEINA, AdjPlato.INTOLERANTE_LACTOSA)
        ),
        Comensal(
            id = "c3",
            nombre = "Elena R.",
            edad = 20,
            facultad = "Bellas Artes",
            bio = "Vegana estricta. Busco compañeros para descubrir cafeterías de especialidad y sitios veganos chulos.",
            avatarEmoji = "🎨",
            personalidad = arrayOf(AdjPersonas.ROMANTICO),
            dietasYGustos = arrayOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE)
        ),
        Comensal(
            id = "c4",
            nombre = "Javi T.",
            edad = 22,
            facultad = "Económicas",
            bio = "Tapas de toda la vida y charlar entre clase y clase. ¡Siempre me apunto a un tapeo después de los exámenes!",
            avatarEmoji = "🥪",
            personalidad = arrayOf(AdjPersonas.FIESTERO),
            dietasYGustos = arrayOf(AdjPlato.SALUDABLE)
        )
    )

    override fun obtenerCandidatos(): List<Comensal> {
        return candidatos
    }

    override fun proponerQuedada(comensalId: String, mensaje: String): Boolean {
        // Simulación: en el Hito 3 enviará la notificación al servidor
        return true
    }
}