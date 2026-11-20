import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante
import com.pad.dishmatch.model.Perfil

private const val MAX_BIO = 200

@Composable
fun PerfilScreen(
    perfil: Perfil,
    onGuardar: (Perfil) -> Unit,
    onVolverClick: () -> Unit,
    onCerrarSesion: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf(perfil.nombre) }
    var biografia by remember { mutableStateOf(perfil.biografia) }
    var dieta by remember { mutableStateOf(perfil.dieta) }
    var personalidad by remember { mutableStateOf(perfil.personalidad) }
    var busco by remember { mutableStateOf(perfil.busco) }
    var estilos by remember { mutableStateOf(perfil.estilosRestaurante) }

    // Sin horizontalAlignment: todo alineado a la izquierda.
    // Centrando cada fila, cada casilla quedaba a una sangria distinta.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = onVolverClick) {
            Text("← Volver")
        }

        Text(
            text = "Tu perfil",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Text(
            text = "Esto es lo que usamos para recomendarte sitios y personas.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        TituloSeccion("DATOS BÁSICOS")

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = biografia,
            onValueChange = { if (it.length <= MAX_BIO) biografia = it },
            label = { Text("Biografía") },
            placeholder = { Text("¿Qué te gusta comer? ¿Cómo eres en la mesa?") },
            supportingText = {
                Text(
                    text = "${biografia.length}/$MAX_BIO",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Seccion("DIETA Y ALERGIAS") {
            for (opcion in AdjPlato.entries) {
                FilaOpcion(opcion.adj, opcion in dieta) { marcada ->
                    dieta = if (marcada) dieta + opcion else dieta - opcion
                }
            }
        }

        Seccion("CÓMO ERES") {
            for (opcion in AdjPersonas.entries) {
                FilaOpcion(opcion.adj, opcion in personalidad) { marcada ->
                    personalidad = if (marcada) personalidad + opcion else personalidad - opcion
                }
            }
        }

        Seccion("A QUIÉN BUSCAS") {
            for (opcion in AdjPersonas.entries) {
                FilaOpcion(opcion.adj, opcion in busco) { marcada ->
                    busco = if (marcada) busco + opcion else busco - opcion
                }
            }
        }

        Seccion("RESTAURANTES QUE TE GUSTAN") {
            for (opcion in AdjRestaurante.entries) {
                FilaOpcion(opcion.adj, opcion in estilos) { marcada ->
                    estilos = if (marcada) estilos + opcion else estilos - opcion
                }
            }
        }

        Button(
            onClick = {
                onGuardar(
                    perfil.copy(
                        nombre = nombre,
                        biografia = biografia,
                        dieta = dieta,
                        personalidad = personalidad,
                        busco = busco,
                        estilosRestaurante = estilos
                    )
                )
                onVolverClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Guardar")
        }

        TextButton(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }

        HorizontalDivider()

        TextButton(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cerrar sesión",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun TituloSeccion(titulo: String) {
    Text(
        text = titulo,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/** Titulo de seccion y las casillas agrupadas dentro de una tarjeta */
@Composable
private fun Seccion(titulo: String, contenido: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TituloSeccion(titulo)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        ) {
            Column(modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp)) {
                contenido()
            }
        }
    }
}

/** Una casilla: se puede pulsar toda la fila, no solo el cuadradito */
@Composable
private fun FilaOpcion(
    texto: String,
    marcada: Boolean,
    onCambiar: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCambiar(!marcada) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = marcada,
            onCheckedChange = onCambiar
        )
        Text(text = texto)
    }
}
