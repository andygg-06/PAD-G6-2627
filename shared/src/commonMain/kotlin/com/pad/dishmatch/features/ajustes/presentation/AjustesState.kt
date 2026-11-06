package com.pad.dishmatch.features.ajustes.presentation

import com.pad.dishmatch.features.ajustes.domain.Ajustes

data class AjustesState(
    val ajustes: Ajustes = Ajustes(),
    val guardadoExitoso: Boolean = false
)