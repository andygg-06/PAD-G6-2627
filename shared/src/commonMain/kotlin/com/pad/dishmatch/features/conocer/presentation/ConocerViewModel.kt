package com.pad.dishmatch.features.conocer.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.conocer.data.ConocerRepositoryImpl
import com.pad.dishmatch.features.conocer.domain.Comensal
import com.pad.dishmatch.features.conocer.domain.ConocerRepository
import com.pad.dishmatch.model.AdjPlato
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConocerViewModel(
    private val repository: ConocerRepository = ConocerRepositoryImpl()
) : ViewModel() {

    private val todosLosCandidatos: List<Comensal> = repository.obtenerCandidatos()

    private val _state = MutableStateFlow(
        ConocerState(candidatos = todosLosCandidatos)
    )
    val state = _state.asStateFlow()

    fun onAction(action: ConocerAction) {
        when (action) {
            is ConocerAction.DescartarCandidato -> {
                _state.update { it.copy(indiceActual = it.indiceActual + 1, mensajeFeedback = null) }
            }

            is ConocerAction.ConectarQuedada -> {
                repository.proponerQuedada(
                    comensalId = action.comensal.id,
                    mensaje = "¡Hola! Me gustaría que fuéramos a comer juntos."
                )
                _state.update {
                    it.copy(
                        indiceActual = it.indiceActual + 1,
                        mensajeFeedback = "¡Propuesta de comida enviada a ${action.comensal.nombre}! 🍽️"
                    )
                }
            }

            is ConocerAction.FiltrarPorDieta -> {
                _state.update { estadoActual ->
                    val nuevaDieta = if (estadoActual.filtroDieta == action.dieta) null else action.dieta
                    val filtrados = if (nuevaDieta == null) {
                        todosLosCandidatos
                    } else {
                        todosLosCandidatos.filter { comensal ->
                            comensal.dietasYGustos.contains(nuevaDieta)
                        }
                    }
                    estadoActual.copy(
                        filtroDieta = nuevaDieta,
                        candidatos = filtrados,
                        indiceActual = 0,
                        mensajeFeedback = null
                    )
                }
            }

            is ConocerAction.ReiniciarCandidatos -> {
                _state.update { it.copy(indiceActual = 0, mensajeFeedback = null) }
            }

            is ConocerAction.LimpiarFeedback -> {
                _state.update { it.copy(mensajeFeedback = null) }
            }

            is ConocerAction.VolverAPrincipal -> {
                // Navegación gestionada por la vista
            }
        }
    }
}