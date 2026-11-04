package com.pad.dishmatch.features.ajustes.domain

interface AjustesRepository {
    fun obtenerAjustes(): Ajustes
    fun guardarAjustes(ajustes: Ajustes)
}