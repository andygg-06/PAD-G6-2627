package com.pad.dishmatch.features.mapa.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pad.dishmatch.features.mapa.domain.PuntoMapa
import com.pad.dishmatch.features.mapa.domain.TipoPunto

/**
 * ScreenRoot: Conecta el ViewModel con la vista Compose pura
 */
@Composable
fun MapaScreenRoot(
    viewModel: MapaViewModel,
    onVolverClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MapaScreen(
        state = state,
        onAction = viewModel::onAction,
        onVolverClick = onVolverClick
    )
}

/**
 * Vista pura del Mapa interactivo (basada en la Ilustración 5 del PDF de diseño)
 */
@Composable
fun MapaScreen(
    state: MapaState,
    onAction: (MapaAction) -> Unit,
    onVolverClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onVolverClick) {
                    Text("← Volver")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "MAPA DE RESTAURANTES Y CITAS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "Madrid Centro • Radio: ${state.filtroDistanciaMax}m",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(60.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Filtros de distancia rápida
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = state.filtroDistanciaMax == 2000,
                        onClick = { onAction(MapaAction.CambiarFiltroDistancia(2000)) },
                        label = { Text("Todos (< 2 km)") }
                    )
                    FilterChip(
                        selected = state.filtroDistanciaMax == 700,
                        onClick = { onAction(MapaAction.CambiarFiltroDistancia(700)) },
                        label = { Text("Cercanos (< 700m)") }
                    )
                    FilterChip(
                        selected = state.filtroDistanciaMax == 400,
                        onClick = { onAction(MapaAction.CambiarFiltroDistancia(400)) },
                        label = { Text("Muy cerca (< 400m)") }
                    )
                }
            }

            // Visor visual del mapa interactivo
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1EFE7))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Dibujo de fondo de cuadrícula de calles de Madrid
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Calles principales (Gran Vía, Fuencarral, Alcalá)
                            drawLine(
                                color = Color(0xFFDDD8CB),
                                start = Offset(0f, h * 0.5f),
                                end = Offset(w, h * 0.5f),
                                strokeWidth = 8f
                            )
                            drawLine(
                                color = Color(0xFFDDD8CB),
                                start = Offset(w * 0.45f, 0f),
                                end = Offset(w * 0.45f, h),
                                strokeWidth = 8f
                            )
                            drawLine(
                                color = Color(0xFFE4DFD5),
                                start = Offset(0f, h * 0.25f),
                                end = Offset(w, h * 0.75f),
                                strokeWidth = 5f
                            )
                        }

                        // Etiquetas de barrios de Madrid
                        Text(
                            text = "MALASAÑA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E998B),
                            modifier = Modifier.padding(start = 24.dp, top = 20.dp)
                        )
                        Text(
                            text = "CHUECA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E998B),
                            modifier = Modifier.align(Alignment.TopEnd).padding(end = 24.dp, top = 20.dp)
                        )
                        Text(
                            text = "SOL / CENTRO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E998B),
                            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
                        )

                        // Renderizado de pines sobre el mapa
                        state.puntos.forEachIndexed { index, punto ->
                            val esSeleccionado = punto.id == state.puntoSeleccionado?.id

                            // Distribución proporcional en el plano
                            val xBias = when (index) {
                                0 -> 0.25f // El Bistro de Pedro (Malasaña)
                                1 -> 0.40f // La Chulapa (Centro)
                                2 -> 0.75f // Wok & Roll (Chueca)
                                3 -> 0.52f // Cita Carlos (Sol)
                                4 -> 0.30f // Cita Ana (2 de Mayo)
                                else -> 0.5f
                            }
                            val yBias = when (index) {
                                0 -> 0.30f
                                1 -> 0.65f
                                2 -> 0.35f
                                3 -> 0.78f
                                4 -> 0.18f
                                else -> 0.5f
                            }

                            val pinColor = if (punto.tipo == TipoPunto.RESTAURANTE) {
                                Color(0xFFE65100) // Naranja restaurante
                            } else {
                                Color(0xFF1E88E5) // Azul cita
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = (xBias * 280).dp,
                                        top = (yBias * 240).dp
                                    )
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable { onAction(MapaAction.SeleccionarPunto(punto)) }
                                ) {
                                    // Burbuja del pin
                                    Box(
                                        modifier = Modifier
                                            .size(if (esSeleccionado) 40.dp else 32.dp)
                                            .clip(CircleShape)
                                            .background(if (esSeleccionado) Color.Black else pinColor)
                                            .border(
                                                width = 2.dp,
                                                color = Color.White,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = if (punto.tipo == TipoPunto.RESTAURANTE) "🍴" else "👥",
                                            fontSize = if (esSeleccionado) 18.sp else 14.sp
                                        )
                                    }
                                    // Nombre flotante
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (esSeleccionado) Color.Black else Color.White.copy(alpha = 0.9f),
                                        shadowElevation = 2.dp
                                    ) {
                                        Text(
                                            text = punto.nombre,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (esSeleccionado) Color.White else Color.Black,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tarjeta de información del punto seleccionado
            state.puntoSeleccionado?.let { seleccionado ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = seleccionado.nombre,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                SuggestionChip(
                                    onClick = { },
                                    label = { Text(if (seleccionado.tipo == TipoPunto.RESTAURANTE) "Restaurante" else "Cita") }
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = seleccionado.descripcion, fontSize = 13.sp)

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "📍 ${seleccionado.direccion}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "★ ${seleccionado.valoracion}  (${seleccionado.precio})", fontSize = 13.sp)
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            val minutosPie = (seleccionado.distanciaMetros / 80).coerceAtLeast(1)
                            Text(
                                text = "🚶 Distancia: ${seleccionado.distanciaMetros} m (aprox. $minutosPie min a pie)",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Lista inferior de todos los lugares
            item {
                Text(
                    text = "LUGARES ENCONTRADOS (${state.puntos.size})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            items(state.puntos, key = { it.id }) { punto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(MapaAction.SeleccionarPunto(punto)) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (punto.id == state.puntoSeleccionado?.id) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = punto.nombre, fontWeight = FontWeight.Bold)
                            Text(text = punto.direccion, fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(
                            text = "${punto.distanciaMetros}m →",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}