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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Plato
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
    var estilo by remember { mutableStateOf<AdjRestaurante?>(null) }
    var abierto by remember { mutableStateOf<Restaurante?>(null) }
    var soloAptos by remember { mutableStateOf(false) }
    val dietas = if (persona != null) miPerfil.dieta + persona.dieta else miPerfil.dieta
    val estilos = miPerfil.estilosRestaurante
    var luegoPersona by remember { mutableStateOf(false) }

    val lista = restaurantes
        .filter { soloAptos || texto.isBlank() || it.nom.contains(texto, ignoreCase = true) }
        .filter { soloAptos || estilo == null || estilo in it.estilosRestaurante }
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
        verticalArrangement = Arrangement.spacedBy(12.dp)
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

        if (persona != null) Text("Quedada con ${persona.nombre}: restaurantes recomendados")

        val r = abierto
        if (r == null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = soloAptos,
                    onCheckedChange = { soloAptos = it; texto = ""; estilo = null }
                )
                Spacer(Modifier.width(8.dp))
                Text("Solo los que encajan con mi perfil")
            }

            if (!soloAptos) {
                OutlinedTextField(texto, { texto = it }, label = { Text("Buscar") },
                    singleLine = true, modifier = Modifier.fillMaxWidth())
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (e in AdjRestaurante.entries) {
                        FilterChip(
                            selected = estilo == e,
                            onClick = { estilo = if (estilo == e) null else e },
                            label = { Text(e.adj) }
                        )
                    }
                }
            }
            if (persona == null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = luegoPersona, onCheckedChange = { luegoPersona = it })
                    Spacer(Modifier.width(8.dp))
                    Text("Después, elegir con quién quedar")
                }
            }
            for (rest in lista) {
                Card(Modifier.fillMaxWidth().clickable { abierto = rest }) {
                    Column(Modifier.padding(16.dp)) {
                        Text(rest.nom, style = MaterialTheme.typography.titleMedium)
                        Text("${rest.ubicacion} · ${rest.precioMin}-${rest.precioMax}€")
                    }
                }
            }
        } else {
            Text(r.nom, style = MaterialTheme.typography.headlineSmall)
            for (p in r.carta) {
                Text("${p.nom} - ${p.precio}€ (${p.tipo.joinToString { it.adj }})")
            }
            Button(onClick = { onElegir(r, luegoPersona) }) { Text("Elegir este local") }
        }
    }
}
