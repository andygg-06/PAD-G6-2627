package com.pad.dishmatch.features.ajustes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * ScreenRoot: Conecta el ViewModel con la pantalla composable pura
 */
@Composable
fun AjustesScreenRoot(
    viewModel: AjustesViewModel,
    onVolverClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AjustesScreen(
        state = state,
        onAction = viewModel::onAction,
        onVolverClick = onVolverClick
    )
}

/**
 * Vista pura de Ajustes según la Sección 3.1 y Requisitos Técnicos del Documento de Diseño
 */
@Composable
fun AjustesScreen(
    state: AjustesState,
    onAction: (AjustesAction) -> Unit,
    onVolverClick: () -> Unit
) {
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
                    text = "Ajustes y configuración",
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
            // SECCIÓN: APARIENCIA
            item {
                Text(
                    text = "APARIENCIA Y PERSONALIZACIÓN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Modo oscuro", fontWeight = FontWeight.SemiBold)
                                Text("Interfaz personalizable (Carta diseño)", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = state.ajustes.temaOscuro,
                                onCheckedChange = { onAction(AjustesAction.CambiarTemaOscuro(it)) }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Idioma (Mr. Worldwide)", fontWeight = FontWeight.SemiBold)
                                Text("Idioma de la aplicación", fontSize = 12.sp, color = Color.Gray)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FilterChip(
                                    selected = state.ajustes.idioma == "Español",
                                    onClick = { onAction(AjustesAction.CambiarIdioma("Español")) },
                                    label = { Text("ES") }
                                )
                                FilterChip(
                                    selected = state.ajustes.idioma == "English",
                                    onClick = { onAction(AjustesAction.CambiarIdioma("English")) },
                                    label = { Text("EN") }
                                )
                            }
                        }
                    }
                }
            }

            // SECCIÓN: NOTIFICACIONES
            item {
                Text(
                    text = "NOTIFICACIONES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Notificaciones de matches", fontWeight = FontWeight.SemiBold)
                                Text("Avisar cuando alguien haga match contigo", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = state.ajustes.notificacionesHabilitadas,
                                onCheckedChange = { onAction(AjustesAction.CambiarNotificaciones(it)) }
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Recordatorios de citas", fontWeight = FontWeight.SemiBold)
                                Text("Alertas previas a la hora de la comida", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = state.ajustes.avisosCitas,
                                onCheckedChange = { onAction(AjustesAction.CambiarAvisosCitas(it)) }
                            )
                        }
                    }
                }
            }

            // SECCIÓN: BÚSQUEDA Y RADAR
            item {
                Text(
                    text = "PREFERENCIAS DE BÚSQUEDA",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Radio máximo de búsqueda", fontWeight = FontWeight.SemiBold)
                            Text(
                                text = "${state.ajustes.radioBusquedaKm.toInt()} km",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = state.ajustes.radioBusquedaKm,
                            onValueChange = { onAction(AjustesAction.CambiarRadioBusqueda(it)) },
                            valueRange = 1f..20f,
                            steps = 18
                        )
                        Text(
                            text = "Filtra restaurantes y personas afines dentro de este rango",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // SECCIÓN: ACERCA DE
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "DishMatch v1.0 • Hito 2",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Programación de Aplicaciones para Dispositivos Móviles\nUniversidad Complutense de Madrid • Grupo 06",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // BOTÓN RESTABLECER
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onAction(AjustesAction.RestablecerValores) }
                    ) {
                        Text("Restablecer valores por defecto")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}