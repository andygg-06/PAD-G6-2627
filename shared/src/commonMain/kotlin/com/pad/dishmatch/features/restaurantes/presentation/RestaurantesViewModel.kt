package com.pad.dishmatch.features.restaurantes.presentation

import androidx.lifecycle.ViewModel
import com.pad.dishmatch.features.restaurantes.data.RestauranteRepositoryImpl
import com.pad.dishmatch.features.restaurantes.domain.RestauranteRepository
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Restaurante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RestaurantesViewModel(
    private val repository: RestauranteRepository = RestauranteRepositoryImpl()
) : ViewModel() {

    // Todos los restaurantes que existen en el repositorio
    private val todosLosRestaurantes: List<Restaurante> = repository.obtenerRestaurantes()

    // Estado observable que consumirá Compose
    private val _state = MutableStateFlow(
        RestaurantesState(restaurantes = todosLosRestaurantes)
    )
    val state = _state.asStateFlow()
    
     //Única puerta de entrada para todas las acciones del usuario (Patrón MVI)

    fun onAction(action: RestaurantesAction) {
        when (action) {
            is RestaurantesAction.CambiarTextoBusqueda -> {
                _state.update { estadoActual ->
                    val nuevoTexto = action.nuevoTexto
                    estadoActual.copy(
                        textoBusqueda = nuevoTexto,
                        restaurantes = filtrar(
                            texto = nuevoTexto,
                            estilo = estadoActual.filtroEstilo,
                            dieta = estadoActual.filtroDieta
                        )
                    )
                }
            }

            is RestaurantesAction.SeleccionarFiltroEstilo -> {
                _state.update { estadoActual ->
                    // Si ya estaba seleccionado, se deselecciona; si no, se activa
                    val nuevoEstilo = if (estadoActual.filtroEstilo == action.estilo) null else action.estilo
                    estadoActual.copy(
                        filtroEstilo = nuevoEstilo,
                        restaurantes = filtrar(
                            texto = estadoActual.textoBusqueda,
                            estilo = nuevoEstilo,
                            dieta = estadoActual.filtroDieta
                        )
                    )
                }
            }

            is RestaurantesAction.SeleccionarFiltroDieta -> {
                _state.update { estadoActual ->
                    val nuevaDieta = if (estadoActual.filtroDieta == action.dieta) null else action.dieta
                    estadoActual.copy(
                        filtroDieta = nuevaDieta,
                        restaurantes = filtrar(
                            texto = estadoActual.textoBusqueda,
                            estilo = estadoActual.filtroEstilo,
                            dieta = nuevaDieta
                        )
                    )
                }
            }

            is RestaurantesAction.ClickRestaurante -> {
                _state.update { it.copy(restauranteSeleccionado = action.restaurante) }
            }

            is RestaurantesAction.CerrarDetalleRestaurante -> {
                _state.update { it.copy(restauranteSeleccionado = null) }
            }

            is RestaurantesAction.VolverAPrincipal -> {
                // Esta acción la gestionará la navegación en la UI
            }
        }
    }

     //Lógica de filtrado en memoria

    private fun filtrar(
        texto: String,
        estilo: AdjRestaurante?,
        dieta: AdjPlato?
    ): List<Restaurante> {
        return todosLosRestaurantes.filter { rest ->
            // 1. Filtro por texto (en nombre o ubicación)
            val coincideTexto = texto.isBlank() ||
                    rest.nom.contains(texto, ignoreCase = true) ||
                    rest.ubicacion.contains(texto, ignoreCase = true)

            // 2. Filtro por estilo de restaurante (urbano, tradicional, etc.)
            val coincideEstilo = estilo == null || rest.estilosRestaurante.contains(estilo)

            // 3. Filtro por dieta/alérgeno en la carta (ej. que tenga algún plato apto para celíacos/vegano)
            val coincideDieta = dieta == null || rest.Carta.any { plato ->
                plato.tipo.contains(dieta)
            }

            coincideTexto && coincideEstilo && coincideDieta
        }
    }
}