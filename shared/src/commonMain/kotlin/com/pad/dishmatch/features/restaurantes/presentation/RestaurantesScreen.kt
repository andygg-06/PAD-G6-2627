package com.pad.dishmatch.features.restaurantes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Perfil
import com.pad.dishmatch.model.Plato
import com.pad.dishmatch.model.Restaurante

private val restaurantes = listOf(
    Restaurante(
        nom = "La Cantina Universitaria",
        ubicacion = "Ciudad Universitaria (Moncloa)",
        precioMin = 8,
        precioMax = 14,
        estilosRestaurante = arrayOf(AdjRestaurante.URBANO, AdjRestaurante.TRADICIONAL),
        Carta = arrayOf(
            Plato("Ensalada Completa de Quinoa", 8, arrayOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE)),
            Plato("Pechuga Asada con Arroz Integral", 10, arrayOf(AdjPlato.PROTEINA, AdjPlato.SALUDABLE)),
            Plato("Tortilla Española Casera", 7, arrayOf(AdjPlato.VEGETARIANO))
        )
    ),
    Restaurante(
        nom = "Verde & Sano Poke Bar",
        ubicacion = "Calle Princesa, 24",
        precioMin = 10,
        precioMax = 18,
        estilosRestaurante = arrayOf(AdjRestaurante.URBANO),
        Carta = arrayOf(
            Plato("Poke Bowl Salmón Fresco", 13, arrayOf(AdjPlato.PROTEINA, AdjPlato.ALERGIA_PESCADO)),
            Plato("Bowl Tofu y Aguacate", 11, arrayOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE)),
            Plato("Smoothie Bowl Proteico", 7, arrayOf(AdjPlato.PROTEINA, AdjPlato.INTOLERANTE_LACTOSA))
        )
    ),
    Restaurante(
        nom = "Trattoria Bella Vista",
        ubicacion = "Plaza de España, 5",
        precioMin = 15,
        precioMax = 25,
        estilosRestaurante = arrayOf(AdjRestaurante.ELEGANTE, AdjRestaurante.TRADICIONAL),
        Carta = arrayOf(
            Plato("Pizza Margherita al Horno", 12, arrayOf(AdjPlato.VEGETARIANO, AdjPlato.INTOLERANTE_LACTOSA)),
            Plato("Pasta Carbonara Tradicional", 14, arrayOf(AdjPlato.INTOLERANTE_LACTOSA)),
            Plato("Tiramisú Casero", 6, arrayOf(AdjPlato.INTOLERANTE_LACTOSA))
        )
    )
)
@Composable
fun RestauranteScreen(
    miPerfil: Perfil,
    persona: Perfil?,
    onVolverClick: () -> Unit,
    onElegir: (Restaurante) -> Unit
) {
    var texto by remember { mutableStateOf("") }
    var estilo by remember { mutableStateOf<AdjRestaurante?>(null) }
    var abierto by remember { mutableStateOf<Restaurante?>(null) }
    var soloAptos by remember { mutableStateOf(false) }
    val dietas = if (persona != null) miPerfil.dieta + persona.dieta else miPerfil.dieta
    val estilos = miPerfil.estilosRestaurante

    val lista = restaurantes
        .filter { soloAptos || texto.isBlank() || it.nom.contains(texto, ignoreCase = true) }
        .filter { soloAptos || estilo == null || estilo in it.estilosRestaurante }
        .filter { r ->
            !soloAptos || (
                    (estilos.isEmpty() || r.estilosRestaurante.any { it in estilos }) &&
                            (dietas.isEmpty() || r.Carta.any { p -> p.tipo.any { it in dietas } })
                    )
        }
        .sortedByDescending { r ->
            r.estilosRestaurante.count { it in estilos } +
                    r.Carta.count { p -> p.tipo.any { it in dietas } }
        }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(onClick = {
            if (abierto != null) abierto = null else onVolverClick()
        }) { Text("← Volver") }

        if (persona != null) Text("Quedada con ${persona.nombre}: restaurantes recomendados")

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = soloAptos,
                onCheckedChange = { soloAptos = it; texto = ""; estilo = null }
            )
            Spacer(Modifier.width(8.dp))
            Text("Solo los que encajan con mi perfil")
        }

        val r = abierto
        if (r == null) {
            if (!soloAptos) {
                OutlinedTextField(texto, { texto = it }, label = { Text("Buscar") },
                    singleLine = true, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (e in AdjRestaurante.entries) {
                        FilterChip(selected = estilo == e,
                            onClick = { estilo = if (estilo == e) null else e },
                            label = { Text(e.adj) })
                    }
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
            for (p in r.Carta) {
                Text("${p.nom} - ${p.precio}€ (${p.tipo.joinToString { it.adj }})")
            }
            Button(onClick = { onElegir(r) }) { Text("Elegir este local") }
        }
    }
}