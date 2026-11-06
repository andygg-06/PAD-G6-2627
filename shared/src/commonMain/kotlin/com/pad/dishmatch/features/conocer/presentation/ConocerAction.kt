package com.pad.dishmatch.features.conocer.presentation

import com.pad.dishmatch.features.conocer.domain.Comensal
import com.pad.dishmatch.model.AdjPlato

sealed interface ConocerAction {
    data object DescartarCandidato : ConocerAction
    data class ConectarQuedada(val comensal: Comensal) : ConocerAction
    data class FiltrarPorDieta(val dieta: AdjPlato?) : ConocerAction
    data object ReiniciarCandidatos : ConocerAction
    data object LimpiarFeedback : ConocerAction
    data object VolverAPrincipal : ConocerAction
}