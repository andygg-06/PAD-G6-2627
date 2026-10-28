import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.model.AdjPersonas
import com.pad.dishmatch.model.AdjPlato
import com.pad.dishmatch.model.AdjRestaurante

import com.pad.dishmatch.model.Perfil

@Composable
fun PerfilScreen(
    perfil: Perfil,
    onGuardar: (Perfil) -> Unit,
    onVolverClick: () -> Unit
) {
    var nombre by remember { mutableStateOf(perfil.nombre) }
    var biografia by remember { mutableStateOf(perfil.biografia) }
    var dieta by remember { mutableStateOf(perfil.dieta) }
    var personalidad by remember { mutableStateOf(perfil.personalidad) }
    var busco by remember { mutableStateOf(perfil.busco) }
    var estilos by remember { mutableStateOf(perfil.estilosRestaurante) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = biografia,
            onValueChange = { biografia = it },
            label = { Text("Biografía") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Dieta y alergias")
        for (opcion in AdjPlato.entries) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = opcion in dieta,
                    onCheckedChange = { marcada ->
                        dieta = if (marcada) dieta + opcion else dieta - opcion
                    }
                )
                Text(opcion.adj)
            }
        }

        Text("Cómo eres")
        for (opcion in AdjPersonas.entries) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = opcion in personalidad,
                    onCheckedChange = { marcada ->
                        personalidad = if (marcada) personalidad + opcion else personalidad - opcion
                    }
                )
                Text(opcion.adj)
            }
        }

        Text("A quién buscas")
        for (opcion in AdjPersonas.entries) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = opcion in busco,
                    onCheckedChange = { marcada ->
                        busco = if (marcada) busco + opcion else busco - opcion
                    }
                )
                Text(opcion.adj)
            }
        }

        Text("Restaurantes que te gustan")
        for (opcion in AdjRestaurante.entries) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = opcion in estilos,
                    onCheckedChange = { marcada ->
                        estilos = if (marcada) estilos + opcion else estilos - opcion
                    }
                )
                Text(opcion.adj)
            }
        }

        Button(onClick = {
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
        }) {
            Text("Guardar")
        }

        TextButton(onClick = onVolverClick) {
            Text("Cancelar")
        }
    }
}