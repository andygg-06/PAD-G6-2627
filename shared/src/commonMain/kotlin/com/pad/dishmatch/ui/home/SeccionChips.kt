package com.pad.dishmatch.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SeccionChips(
    titulo: String,
    opciones: List<T>,
    seleccionadas: Set<T>,
    texto: (T) -> String,
    onCambio: (Set<T>) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(titulo, style = MaterialTheme.typography.titleMedium)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (opcion in opciones) {
                FilterChip(
                    selected = opcion in seleccionadas,
                    onClick = {
                        onCambio(
                            if (opcion in seleccionadas) seleccionadas - opcion
                            else seleccionadas + opcion
                        )
                    },
                    label = { Text(texto(opcion)) }
                )
            }
        }
    }
}