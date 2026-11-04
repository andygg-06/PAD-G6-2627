package com.pad.dishmatch.features.conocer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pad.dishmatch.features.conocer.domain.Comensal
import com.pad.dishmatch.model.AdjPlato

@Composable
fun ConocerScreenRoot(
    viewModel: ConocerViewModel,
    onVolverClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ConocerScreen(
        state = state,
        onAction = { action ->
            if (action is ConocerAction.VolverAPrincipal) {
                onVolverClick()
            } else {
                viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConocerScreen(
    state: ConocerState,
    onAction: (ConocerAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Barra superior con botón volver y título
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { onAction(ConocerAction.VolverAPrincipal) }) {
                Text("← Volver")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = "Compañeros de Comida",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Afinidad gastronómica",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtro rápido por dieta
        Text("Filtrar candidatos por dieta:", style = MaterialTheme.typography.labelMedium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            items(listOf(AdjPlato.VEGANO, AdjPlato.VEGETARIANO, AdjPlato.PROTEINA, AdjPlato.SALUDABLE)) { dieta ->
                FilterChip(
                    selected = state.filtroDieta == dieta,
                    onClick = { onAction(ConocerAction.FiltrarPorDieta(dieta)) },
                    label = { Text(dieta.adj) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mensaje de éxito si acabas de enviar una propuesta
        if (state.mensajeFeedback != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Text(
                    text = state.mensajeFeedback,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Tarjeta del comensal o mensaje de fin
        val comensal = state.comensalActual
        if (comensal != null) {
            TarjetaComensal(
                comensal = comensal,
                onDescartar = { onAction(ConocerAction.DescartarCandidato) },
                onConectar = { onAction(ConocerAction.ConectarQuedada(comensal)) }
            )
        } else {
            FinDeCandidatosCard(
                onReiniciar = { onAction(ConocerAction.ReiniciarCandidatos) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TarjetaComensal(
    comensal: Comensal,
    onDescartar: () -> Unit,
    onConectar: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar con emoji y fondo circular
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = comensal.avatarEmoji, fontSize = 42.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre y edad
            Text(
                text = "${comensal.nombre}, ${comensal.edad} años",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Facultad
            Text(
                text = "🎓 ${comensal.facultad}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Biografía / Quote
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "\"${comensal.bio}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Etiquetas de dieta y personalidad
            Text(
                text = "Gustos y afinidades:",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.Start)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            ) {
                for (dieta in comensal.dietasYGustos) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text("🥗 ${dieta.adj}") }
                    )
                }
                for (pers in comensal.personalidad) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text("✨ ${pers.adj}") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones de acción (Descartar vs Conectar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDescartar,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("❌ Pasar")
                }

                Button(
                    onClick = onConectar,
                    modifier = Modifier.weight(1.3f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("🍽️ ¡Quedar!")
                }
            }
        }
    }
}

@Composable
private fun FinDeCandidatosCard(
    onReiniciar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🎉", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "¡Has visto a todos los comensales disponibles!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Vuelve a revisar o prueba con otros filtros.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Button(onClick = onReiniciar) {
                Text("Volver a ver perfiles")
            }
        }
    }
}