import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.pad.dishmatch.ui.home.SeccionChips

@Composable
fun PerfilScreen(
    perfil: Perfil,
    onGuardar: (Perfil) -> Unit,
    onVolverClick: () -> Unit
) {
    var borrador by remember { mutableStateOf(perfil) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // tu Box de la foto igual que ahora

        OutlinedTextField(
            value = borrador.nombre,
            onValueChange = { borrador = borrador.copy(nombre = it) },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = borrador.biografia,
            onValueChange = { borrador = borrador.copy(biografia = it) },
            label = { Text("Biografía") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        SeccionChips("Dieta y alergias", Dieta.entries, borrador.dieta, { it.texto }) {
            borrador = borrador.copy(dieta = it)
        }
        SeccionChips("Cómo eres", Personalidad.entries, borrador.personalidad, { it.texto }) {
            borrador = borrador.copy(personalidad = it)
        }
        SeccionChips("A quién buscas", Personalidad.entries, borrador.busco, { it.texto }) {
            borrador = borrador.copy(busco = it)
        }
        SeccionChips("Tus restaurantes", EstiloRestaurante.entries, borrador.estilosRestaurante, { it.texto }) {
            borrador = borrador.copy(estilosRestaurante = it)
        }

        Button(onClick = { onGuardar(borrador); onVolverClick() }) { Text("Guardar") }
        TextButton(onClick = onVolverClick) { Text("Cancelar") }
    }
}