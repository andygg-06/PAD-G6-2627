package com.pad.dishmatch.features.restaurantes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Plato
import com.pad.dishmatch.model.Restaurante

/**
 * ScreenRoot: Conecta el ViewModel con la vista Compose pura
 */
@Composable
fun RestaurantesScreenRoot(
    viewModel: RestaurantesViewModel,
    onVolverClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RestaurantesScreen(
        state = state,
        onAction = { action ->
            if (action is RestaurantesAction.VolverAPrincipal) {
                onVolverClick()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

/**
 * Screen: Composable puro e independiente. Solo lee State y emite Actions.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RestaurantesScreen(
    state: RestaurantesState,
    onAction: (RestaurantesAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Encabezado con botón de volver
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = {
                    if (state.restauranteSeleccionado != null) {
                        onAction(RestaurantesAction.CerrarDetalleRestaurante)
                    } else {
                        onAction(RestaurantesAction.VolverAPrincipal)
                    }
                }
            ) {
                Text("← Volver")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = if (state.restauranteSeleccionado != null) "Carta del Restaurante" else "Descubrir Restaurantes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Si hay un restaurante seleccionado, mostramos su carta de platos en detalle
        if (state.restauranteSeleccionado != null) {
            DetalleRestauranteView(
                restaurante = state.restauranteSeleccionado,
                onCerrar = { onAction(RestaurantesAction.CerrarDetalleRestaurante) }
            )
        } else {
            // Si no, mostramos el buscador, los filtros y la lista de locales
            BuscadorYFiltros(
                texto = state.textoBusqueda,
                filtroEstilo = state.filtroEstilo,
                filtroDieta = state.filtroDieta,
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(12.dp))

            ListaRestaurantesView(
                restaurantes = state.restaurantes,
                onRestauranteClick = { onAction(RestaurantesAction.ClickRestaurante(it)) }
            )
        }
    }
}

@Composable
private fun BuscadorYFiltros(
    texto: String,
    filtroEstilo: AdjRestaurante?,
    filtroDieta: AdjPlato?,
    onAction: (RestaurantesAction) -> Unit
) {
    OutlinedTextField(
        value = texto,
        onValueChange = { onAction(RestaurantesAction.CambiarTextoBusqueda(it)) },
        label = { Text("Buscar por nombre o zona...") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text("Filtros rápidos:", style = MaterialTheme.typography.labelMedium)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        items(AdjRestaurante.entries) { estilo ->
            FilterChip(
                selected = filtroEstilo == estilo,
                onClick = { onAction(RestaurantesAction.SeleccionarFiltroEstilo(estilo)) },
                label = { Text(estilo.adj.replaceFirstChar { it.uppercase() }) }
            )
        }
        items(listOf(AdjPlato.VEGANO, AdjPlato.PROTEINA, AdjPlato.SALUDABLE)) { dieta ->
            FilterChip(
                selected = filtroDieta == dieta,
                onClick = { onAction(RestaurantesAction.SeleccionarFiltroDieta(dieta)) },
                label = { Text(dieta.adj) }
            )
        }
    }
}

@Composable
private fun ListaRestaurantesView(
    restaurantes: List<Restaurante>,
    onRestauranteClick: (Restaurante) -> Unit
) {
    if (restaurantes.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No se encontraron restaurantes con estos filtros.")
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(restaurantes) { rest ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRestauranteClick(rest) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = rest.nom,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "📍 ${rest.ubicacion}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Presupuesto: ${rest.precioMin}€ - ${rest.precioMax}€",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            for (estilo in rest.estilosRestaurante) {
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(estilo.adj) }
                                )
                            }
                        }

                        Text(
                            text = "🍽️ ${rest.Carta.size} platos en la carta → Toca para ver",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetalleRestauranteView(
    restaurante: Restaurante,
    onCerrar: () -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = restaurante.nom,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text("📍 ${restaurante.ubicacion}")
                    Text("Precio medio: ${restaurante.precioMin}€ - ${restaurante.precioMax}€")
                }
            }
        }

        item {
            Text(
                text = "Carta de platos y alérgenos:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(restaurante.Carta) { plato ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = plato.nom,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${plato.precio} €",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (plato.tipo.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            for (tag in plato.tipo) {
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(tag.adj) }
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onCerrar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Elegir este local para la quedada")
            }
        }
    }
}