package com.pad.dishmatch.features.ajustes.data

import com.pad.dishmatch.features.ajustes.domain.Ajustes
import com.pad.dishmatch.features.ajustes.domain.AjustesRepository

class AjustesRepositoryImpl : AjustesRepository {
    private var ajustesActuales = Ajustes()

    override fun obtenerAjustes(): Ajustes = ajustesActuales

    override fun guardarAjustes(ajustes: Ajustes) {
        ajustesActuales = ajustes
    }
}