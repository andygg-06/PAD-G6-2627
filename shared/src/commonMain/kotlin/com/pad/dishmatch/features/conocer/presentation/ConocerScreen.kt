import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.Perfil
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Restaurante

val candidatos = listOf(
    Perfil(
        id = "c1", nombre = "Laura M.", edad = 21,
        biografia = "Fanática del ramen y la comida asiática.",
        personalidad = setOf(AdjPersonas.FIESTERO, AdjPersonas.ROMANTICO),
        dieta = setOf(AdjPlato.VEGETARIANO, AdjPlato.SALUDABLE),
        estilosRestaurante = setOf(AdjRestaurante.URBANO, AdjRestaurante.ELEGANTE)
    ),
    Perfil(
        id = "c2", nombre = "Carlos G.", edad = 23,
        biografia = "Busco sitios con platos altos en proteína.",
        personalidad = setOf(AdjPersonas.ALTO),
        dieta = setOf(AdjPlato.PROTEINA, AdjPlato.INTOLERANTE_LACTOSA),
        estilosRestaurante = setOf(AdjRestaurante.URBANO, AdjRestaurante.TRADICIONAL)
    ),
    Perfil(
        id = "c3", nombre = "Elena R.", edad = 20,
        biografia = "Vegana estricta, busco cafeterías chulas.",
        personalidad = setOf(AdjPersonas.ROMANTICO),
        dieta = setOf(AdjPlato.VEGANO, AdjPlato.SALUDABLE),
        estilosRestaurante = setOf(AdjRestaurante.ELEGANTE)
    )
)

@Composable
fun ConocerScreen(
    miPerfil: Perfil,
    restaurante: Restaurante?,
    onVolverClick: () -> Unit,
    onQuedar: (Perfil) -> Unit
)
 {
    var indice by remember { mutableStateOf(0) }
    var filtro by remember { mutableStateOf<AdjPlato?>(null) }
    var mensaje by remember { mutableStateOf<String?>(null) }
    //nuevo, para elegir el filtro
    var usarPerfil by remember { mutableStateOf(false) }
    val lista = if (usarPerfil) {
        candidatos.filter { c ->
            val encajaPersonalidad =
                miPerfil.busco.isEmpty() || c.personalidad.intersect(miPerfil.busco).isNotEmpty()
            val encajaDieta =
                miPerfil.dieta.isEmpty() || c.dieta.intersect(miPerfil.dieta).isNotEmpty()
            encajaPersonalidad && encajaDieta
        }
    } else if (filtro == null) {
        candidatos
    } else {
        candidatos.filter { filtro in it.dieta }
    }
     val listaFinal = if (restaurante == null) lista else lista.filter { c ->
         c.estilosRestaurante.any { it in restaurante.estilosRestaurante }
     }
     val actual = listaFinal.getOrNull(indice)



    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(onClick = onVolverClick) { Text("<- Volver") }
        if (restaurante != null) Text("Cita en ${restaurante.nom}: personas que encajan")

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = usarPerfil,
                onCheckedChange = { usarPerfil = it; indice = 0 }
            )
            Text("Usar los gustos de mi perfil")
        }
        if (!usarPerfil) {
            Text("Filtrar por dieta:")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (d in listOf(AdjPlato.VEGANO, AdjPlato.VEGETARIANO, AdjPlato.PROTEINA)) {
                    FilterChip(
                        selected = filtro == d,
                        onClick = { filtro = if (filtro == d) null else d; indice = 0 },
                        label = { Text(d.adj) }
                    )
                }
            }
        }


        val aviso = mensaje
        if (aviso != null) Text(aviso)

        if (actual == null) {
            Text("Ya has visto a todos, no te pases de ligón")
            Button(onClick = { indice = 0; mensaje = null }) { Text("Volver a ver") }
        } else {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${actual.nombre}, ${actual.edad} años",
                        style = MaterialTheme.typography.headlineSmall)
                    Text(actual.biografia)
                    Text("Dieta: " + actual.dieta.joinToString { it.adj })
                    Text("Personalidad: " + actual.personalidad.joinToString { it.adj })

                    //aquí se conecta con el perfil
                    val coincidencias = actual.dieta.intersect(miPerfil.dieta).size
                    Text("Coincidís en $coincidencias gustos de dieta")

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = { indice++; mensaje = null }) { Text("Pasar") }
                        Button(onClick = { onQuedar(actual) }) { //depende de si tenias cita hecha
                            Text(if (restaurante != null) "¿Aceptar cita en ${restaurante.nom}?" else "🍽️¡Quedar!")
                        }
                    }
                }
            }
        }
    }
}