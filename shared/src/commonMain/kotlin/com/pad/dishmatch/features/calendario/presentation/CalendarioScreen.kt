package com.pad.dishmatch.features.calendario.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * ScreenRoot: Conecta el ViewModel con la pantalla pura
 */
@Composable
fun CalendarioScreenRoot(
    viewModel: CalendarioViewModel,
    onVolverClick: () -> Unit,
    // Si venimos de confirmar un plan, aqui llega el texto que se muestra arriba
    textoPlanPendiente: String? = null,
    onGuardarPlan: (dia: Int, hora: String) -> Unit = { _, _ -> }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Al abrir la pantalla releemos: puede haber planes guardados despues
    LaunchedEffect(Unit) { viewModel.refrescar() }

    CalendarioScreen(
        state = state,
        onAction = viewModel::onAction,
        onVolverClick = onVolverClick,
        textoPlanPendiente = textoPlanPendiente,
        onGuardarPlan = onGuardarPlan
    )
}

/**
 * Vista pura de Calendario (basada en la Ilustración 4 del Documento de Diseño)
 */
@Composable
fun CalendarioScreen(
    state: CalendarioState,
    onAction: (CalendarioAction) -> Unit,
    onVolverClick: () -> Unit,
    textoPlanPendiente: String? = null,
    onGuardarPlan: (dia: Int, hora: String) -> Unit = { _, _ -> }
) {
    var hora by remember { mutableStateOf("21:00") }

    Scaffold(
        topBar = {
            // El boton arriba y el titulo debajo: asi el titulo nunca
            // se queda sin sitio ni se mete debajo del boton
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
            ) {
                OutlinedButton(onClick = onVolverClick) {
                    Text("← Volver")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Tu calendario de citas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Banda de "elige el dia", solo cuando llegamos de confirmar un plan
            if (textoPlanPendiente != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "ELIGE EL DÍA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = textoPlanPendiente,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Toca un día en el calendario y elige la hora.",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FilterChip(
                                    selected = hora == "14:00",
                                    onClick = { hora = "14:00" },
                                    label = { Text("Comida · 14:00") }
                                )
                                FilterChip(
                                    selected = hora == "21:00",
                                    onClick = { hora = "21:00" },
                                    label = { Text("Cena · 21:00") }
                                )
                            }
                            Button(
                                onClick = { onGuardarPlan(state.diaSeleccionado, hora) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Guardar el día ${state.diaSeleccionado} a las $hora")
                            }
                        }
                    }
                }
            }

            // Mes y cabecera del calendario
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "NOVIEMBRE 2026",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Días de la semana
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            listOf("L", "M", "X", "J", "V", "S", "D").forEach { diaSemana ->
                                Text(
                                    text = diaSemana,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Cuadrícula de días (1 al 30)
                        val diasConCita = state.todasLasCitas.map { it.diaMes }.toSet()

                        for (semana in 0..4) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                for (col in 1..7) {
                                    val dia = semana * 7 + col
                                    if (dia <= 30) {
                                        val tieneCita = diasConCita.contains(dia)
                                        val esSeleccionado = dia == state.diaSeleccionado

                                        val bgColor = when {
                                            esSeleccionado -> MaterialTheme.colorScheme.primary
                                            tieneCita -> MaterialTheme.colorScheme.primaryContainer
                                            else -> Color.Transparent
                                        }

                                        val textColor = when {
                                            esSeleccionado -> MaterialTheme.colorScheme.onPrimary
                                            tieneCita -> MaterialTheme.colorScheme.onPrimaryContainer
                                            else -> MaterialTheme.colorScheme.onSurface
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(bgColor)
                                                .clickable { onAction(CalendarioAction.SeleccionarDia(dia)) },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = dia.toString(),
                                                color = textColor,
                                                fontSize = 13.sp,
                                                fontWeight = if (tieneCita || esSeleccionado) FontWeight.Bold else FontWeight.Normal
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(36.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Sección: Citas Destacadas
            item {
                Text(
                    text = "CITAS DESTACADAS (DÍA ${state.diaSeleccionado})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            if (state.citasDelDia.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = "No tienes citas programadas para el día ${state.diaSeleccionado}.\n¡Selecciona el día 14, 21 o 28 para ver tus quedadas!",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(state.citasDelDia, key = { it.id }) { cita ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CITA CON: ${cita.personaNombre.uppercase()}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 15.sp
                                )
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(cita.estado) }
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "📍 DONDE: ${cita.restauranteNombre}",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "    ${cita.ubicacion}",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "🕒 CUANDO: ${cita.fecha}, a las ${cita.hora}h",
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = { onAction(CalendarioAction.CancelarCita(cita.id)) }
                                ) {
                                    Text("Cancelar cita", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }

            // Lista de todas las citas del usuario para verlas de un vistazo
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "TODAS TUS PRÓXIMAS CITAS",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            items(state.todasLasCitas, key = { "todas_${it.id}" }) { cita ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .clickable { onAction(CalendarioAction.SeleccionarDia(cita.diaMes)) }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Cita con ${cita.personaNombre}", fontWeight = FontWeight.SemiBold)
                        Text(text = "${cita.restauranteNombre} • ${cita.fecha}", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text(text = "Día ${cita.diaMes} →", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}