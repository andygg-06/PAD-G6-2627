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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.features.restaurantes.domain.RestauranteRepository
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Restaurante

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RestauranteScreen(
    miPerfil: Perfil,
    persona: Perfil?,
    repository: RestauranteRepository,
    onVolverClick: () -> Unit,
    onCrearRestauranteClick: () -> Unit = {},
    onElegir: (Restaurante, Boolean) -> Unit
) {
    val restaurantes = repository.obtenerRestaurantes()
    var texto by remember { mutableStateOf("") }
    var estilosSeleccionados by remember { mutableStateOf(setOf<AdjRestaurante>()) }
    var rangoPrecio by remember { mutableStateOf(0f..50f) }
    var abierto by remember { mutableStateOf<Restaurante?>(null) }
    var soloAptos by remember { mutableStateOf(false) }
    val dietas = if (persona != null) miPerfil.dieta + persona.dieta else miPerfil.dieta
    val estilos = miPerfil.estilosRestaurante
    var luegoPersona by remember { mutableStateOf(false) }

    // Filtros combinados simultáneos
    val lista = restaurantes
        .filter { texto.isBlank() || it.nom.contains(texto, ignoreCase = true) }
        .filter { estilosSeleccionados.isEmpty() || it.estilosRestaurante.any { e -> e in estilosSeleccionados } }
        .filter { it.precioMin <= rangoPrecio.endInclusive && it.precioMax >= rangoPrecio.start }
        .filter { r ->
            !soloAptos || (
                (estilos.isEmpty() || r.estilosRestaurante.any { it in estilos }) &&
                (dietas.isEmpty() || r.carta.any { p -> p.tipo.any { it in dietas } })
            )
        }
        .sortedByDescending { r ->
            r.estilosRestaurante.count { it in estilos } +
            r.carta.count { p -> p.tipo.any { it in dietas } }
        }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = {
                if (abierto != null) abierto = null else onVolverClick()
            }) { Text("← Volver") }

            if (abierto == null) {
                Button(onClick = onCrearRestauranteClick) {
                    Text("+ Añadir local")
                }
            }
        }

        if (persona != null) {
            Text(
                text = "Quedada con ${persona.nombre}: restaurantes recomendados",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        val r = abierto
        if (r == null) {
            // Buscador por nombre
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Buscar restaurante por nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Deslizable de precios
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rango de precio:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        val maxTexto = if (rangoPrecio.endInclusive >= 50f) "50+ €" else "${rangoPrecio.endInclusive.toInt()} €"
                        Text(
                            text = "${rangoPrecio.start.toInt()} € — $maxTexto",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    RangeSlider(
                        value = rangoPrecio,
                        onValueChange = { rangoPrecio = it },
                        valueRange = 0f..50f,
                        steps = 9
                    )
                }
            }

            // Selección múltiple
            Text("Estilos de comida:", style = MaterialTheme.typography.titleSmall)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (e in AdjRestaurante.entries) {
                    val seleccionado = e in estilosSeleccionados
                    FilterChip(
                        selected = seleccionado,
                        onClick = {
                            estilosSeleccionados = if (seleccionado) {
                                estilosSeleccionados - e
                            } else {
                                estilosSeleccionados + e
                            }
                        },
                        label = { Text(e.adj) }
                    )
                }
            }

            // Switch de compatibilidad
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = soloAptos,
                    onCheckedChange = { soloAptos = it }
                )
                Spacer(Modifier.width(8.dp))
                Text("Solo platos que encajen con mi dieta")
            }

            if (persona == null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = luegoPersona, onCheckedChange = { luegoPersona = it })
                    Spacer(Modifier.width(8.dp))
                    Text("Después, elegir con quién quedar")
                }
            }

            // Contador de resultados
            Text(
                text = "${lista.size} locales encontrados",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Estado vacío si no hay resultados
            if (lista.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No hay restaurantes con estos filtros",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Prueba a ampliar el presupuesto o quitar algún estilo",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Lista de restaurantes encontrados
            for (rest in lista) {
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { abierto = rest },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(rest.nom, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "${rest.precioMin} - ${rest.precioMax} €",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = rest.ubicacion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = rest.estilosRestaurante.joinToString { it.adj },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        } else {
            // Vista de detalle de la carta
            Text(r.nom, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "${r.ubicacion} · Presupuesto aproximado: ${r.precioMin} - ${r.precioMax} €",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text("Carta digital:", style = MaterialTheme.typography.titleMedium)

            for (p in r.carta) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.nom, style = MaterialTheme.typography.titleSmall)
                            Text(
                                text = p.tipo.joinToString { it.adj },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = "${p.precio} €",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            Button(
                onClick = { onElegir(r, luegoPersona) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (persona != null) "Elegir este local para la cita" else "Elegir este local")
            }
        }
    }
}
