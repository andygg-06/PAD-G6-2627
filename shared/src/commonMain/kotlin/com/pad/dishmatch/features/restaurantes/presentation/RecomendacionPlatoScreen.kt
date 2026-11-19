package com.pad.dishmatch.features.restaurantes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Restaurante

@Composable
fun RecomendacionPlatoScreen(
    miPerfil: Perfil,
    persona: Perfil?,
    restaurante: Restaurante?,
    onVolverClick: () -> Unit,
    onConfirmarPlan: () -> Unit
) {
    // Selección del plato con mayor afinidad a la dieta guardada en el perfil
    val platoRecomendado = remember(restaurante, miPerfil) {
        val carta = restaurante?.carta ?: emptyArray()
        if (carta.isEmpty()) return@remember null

        if (miPerfil.dieta.isEmpty()) {
            carta.firstOrNull()
        } else {
            carta.maxByOrNull { plato ->
                plato.tipo.count { it in miPerfil.dieta }
            } ?: carta.firstOrNull()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onVolverClick) {
            Text("← Volver")
        }

        Text(
            text = "Tu plato recomendado",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        // Resumen del contexto de la quedada
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                if (restaurante != null) {
                    Text("📍 Restaurante: ${restaurante.nom} (${restaurante.ubicacion})", fontWeight = FontWeight.SemiBold)
                }
                if (persona != null) {
                    Text("👤 Acompañante: ${persona.nombre}", fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Tarjeta del plato recomendado para el usuario
        if (platoRecomendado != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⭐ Plato sugerido para ti:",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = platoRecomendado.nom,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${platoRecomendado.precio} €",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Etiquetas del plato
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        for (tag in platoRecomendado.tipo) {
                            SuggestionChip(
                                onClick = { },
                                label = { Text(tag.adj) }
                            )
                        }
                    }

                    // Motivo de la recomendación basado en las preferencias guardadas
                    val coincidencias = platoRecomendado.tipo.filter { it in miPerfil.dieta }
                    if (coincidencias.isNotEmpty()) {
                        Text(
                            text = "Apto para tus preferencias: " + coincidencias.joinToString { it.adj },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        Text(
                            text = "Plato destacado de la carta del restaurante.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Otras opciones disponibles en la carta
        if (restaurante != null && restaurante.carta.isNotEmpty()) {
            Text(
                text = "Otros platos disponibles en la carta:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            for (plato in restaurante.carta) {
                if (plato != platoRecomendado) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(plato.nom, fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = plato.tipo.joinToString { it.adj },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text("${plato.precio} €", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de confirmación final
        Button(
            onClick = onConfirmarPlan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar plan")
        }
    }
}