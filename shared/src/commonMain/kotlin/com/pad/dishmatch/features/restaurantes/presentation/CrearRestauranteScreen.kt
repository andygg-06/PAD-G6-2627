package com.pad.dishmatch.features.restaurantes.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Plato
import com.pad.dishmatch.model.Restaurante

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CrearRestauranteScreen(
    onVolverClick: () -> Unit,
    onGuardarRestaurante: (Restaurante) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var precioMin by remember { mutableStateOf("10") }
    var precioMax by remember { mutableStateOf("25") }
    var estilosSeleccionados by remember { mutableStateOf(setOf<AdjRestaurante>()) }

    var cartaPlatos by remember { mutableStateOf(listOf<Plato>()) }
    var nombrePlato by remember { mutableStateOf("") }
    var precioPlato by remember { mutableStateOf("") }
    var etiquetasPlato by remember { mutableStateOf(setOf<AdjPlato>()) }

    var indiceEnEdicion by remember { mutableStateOf<Int?>(null) }

    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedButton(onClick = onVolverClick) {
            Text("← Volver")
        }

        Text(
            text = "Añadir nuevo restaurante",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Datos del local",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; mensajeError = null },
                    label = { Text("Nombre del restaurante") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ubicacion,
                    onValueChange = { ubicacion = it; mensajeError = null },
                    label = { Text("Ubicación o dirección") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = precioMin,
                        onValueChange = { precioMin = it },
                        label = { Text("Precio mín (€)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = precioMax,
                        onValueChange = { precioMax = it },
                        label = { Text("Precio máx (€)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Estilo gastronómico:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (estilo in AdjRestaurante.entries) {
                        val activo = estilo in estilosSeleccionados
                        FilterChip(
                            selected = activo,
                            onClick = {
                                estilosSeleccionados = if (activo) {
                                    estilosSeleccionados - estilo
                                } else {
                                    estilosSeleccionados + estilo
                                }
                            },
                            label = { Text(estilo.adj) }
                        )
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = if (indiceEnEdicion != null) "Editar plato" else "Carta de platos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (indiceEnEdicion != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = nombrePlato,
                        onValueChange = { nombrePlato = it },
                        label = { Text("Nombre del plato") },
                        singleLine = true,
                        modifier = Modifier.weight(2f)
                    )
                    OutlinedTextField(
                        value = precioPlato,
                        onValueChange = { precioPlato = it },
                        label = { Text("Precio (€)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Dietas y alérgenos del plato:",
                    style = MaterialTheme.typography.bodySmall
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (etiqueta in AdjPlato.entries) {
                        val seleccionada = etiqueta in etiquetasPlato
                        FilterChip(
                            selected = seleccionada,
                            onClick = {
                                etiquetasPlato = if (seleccionada) {
                                    etiquetasPlato - etiqueta
                                } else {
                                    etiquetasPlato + etiqueta
                                }
                            },
                            label = { Text(etiqueta.adj) }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (nombrePlato.isNotBlank()) {
                                val precio = precioPlato.toIntOrNull() ?: 8
                                val platoActualizado = Plato(
                                    nom = nombrePlato.trim(),
                                    precio = precio,
                                    tipo = etiquetasPlato.toTypedArray()
                                )
                                val idx = indiceEnEdicion
                                if (idx != null && idx in cartaPlatos.indices) {
                                    // Actualizar el plato existente
                                    cartaPlatos = cartaPlatos.toMutableList().also { it[idx] = platoActualizado }
                                    indiceEnEdicion = null
                                } else {
                                    // Añadir un plato nuevo
                                    cartaPlatos = cartaPlatos + platoActualizado
                                }
                                nombrePlato = ""
                                precioPlato = ""
                                etiquetasPlato = emptySet()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (indiceEnEdicion != null) "✓ Guardar cambios" else "+ Añadir plato")
                    }

                    if (indiceEnEdicion != null) {
                        OutlinedButton(onClick = {
                            indiceEnEdicion = null
                            nombrePlato = ""
                            precioPlato = ""
                            etiquetasPlato = emptySet()
                        }) {
                            Text("Cancelar")
                        }
                    }
                }

                if (cartaPlatos.isNotEmpty()) {
                    Text(
                        text = "Platos añadidos (${cartaPlatos.size}):",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    cartaPlatos.forEachIndexed { index, plato ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(plato.nom, fontWeight = FontWeight.Medium)
                                if (plato.tipo.isNotEmpty()) {
                                    Text(
                                        text = plato.tipo.joinToString { it.adj },
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Text("${plato.precio} €", fontWeight = FontWeight.Bold)
                            
                            // Editar
                            TextButton(onClick = {
                                indiceEnEdicion = index
                                nombrePlato = plato.nom
                                precioPlato = plato.precio.toString()
                                etiquetasPlato = plato.tipo.toSet()
                            }) {
                                Text("✏️")
                            }

                            // Eliminar
                            TextButton(onClick = {
                                if (indiceEnEdicion == index) {
                                    indiceEnEdicion = null
                                    nombrePlato = ""
                                    precioPlato = ""
                                    etiquetasPlato = emptySet()
                                }
                                cartaPlatos = cartaPlatos.filterIndexed { i, _ -> i != index }
                            }) {
                                Text("🗑️")
                            }
                        }
                    }
                }
            }
        }

        if (mensajeError != null) {
            Text(
                text = mensajeError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                val min = precioMin.toIntOrNull() ?: 10
                val max = precioMax.toIntOrNull() ?: 25
                if (nombre.isBlank() || ubicacion.isBlank()) {
                    mensajeError = "Por favor, introduce el nombre y la ubicación del restaurante."
                } else if (min > max) {
                    mensajeError = "El precio mínimo no puede ser mayor que el precio máximo."
                } else if (cartaPlatos.isEmpty()) {
                    mensajeError = "Añade al menos un plato a la carta del restaurante."
                } else {
                    val nuevoRestaurante = Restaurante(
                        nom = nombre.trim(),
                        ubicacion = ubicacion.trim(),
                        precioMin = min,
                        precioMax = max,
                        estilosRestaurante = estilosSeleccionados.toTypedArray(),
                        carta = cartaPlatos.toTypedArray()
                    )
                    onGuardarRestaurante(nuevoRestaurante)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar restaurante")
        }
    }
}
