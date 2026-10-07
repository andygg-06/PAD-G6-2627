package com.pad.dishmatch.ui.home
import dishmatch.shared.generated.resources.Res
import dishmatch.shared.generated.resources.calendario
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.pad.dishmatch.AjustesScreen
import dishmatch.shared.generated.resources.ajustes
import dishmatch.shared.generated.resources.mapa
import org.jetbrains.compose.resources.painterResource
import dishmatch.shared.generated.resources.person
import dishmatch.shared.generated.resources.personas
import dishmatch.shared.generated.resources.comida
import dishmatch.shared.generated.resources.comida2






@Composable

fun  PaginaPrincipal(onIrCalendario: () -> Unit,
                     onIrMapa: () -> Unit,
                     onIrAjustes: () -> Unit,
                     onIrPerfil: () -> Unit,
                     onIrConocer: () -> Unit,
                     onIrRestaurante: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically



    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onIrPerfil) {
                Image(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = "Foto persona",
                    modifier = Modifier
                        .size(70.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Nombre")
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.7f) // Ocupa el 70% del ancho del contenedor
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
            Button(onClick = onIrCalendario) {
                Image(
                    painter = painterResource(Res.drawable.calendario),
                    contentDescription = "Foto calendario",
                    modifier = Modifier
                        .size(50.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Calendario")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onIrMapa) {
                Image(
                    painter = painterResource(Res.drawable.mapa),
                    contentDescription = "Foto mapa ",
                    modifier = Modifier
                        .size(50.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Mapa")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onIrAjustes) {
                Image(
                    painter = painterResource(Res.drawable.ajustes),
                    contentDescription = "Foto ajustes",
                    modifier = Modifier
                        .size(50.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Ajustes")
        }
        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(0.7f) // Ocupa el 70% del ancho del contenedor
                .padding(horizontal = 8.dp), //distancia hacia el texto
            thickness = 1.dp,//grosor
            color = Color.Gray
        )
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Conocer a alguien")
            Button(onClick = onIrConocer) {
                Image(
                    painter = painterResource(Res.drawable.personas),
                    contentDescription = "Foto personas",
                    modifier = Modifier
                        .size(100.dp) // Tamaño de la imagen
                        .clip(RectangleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Elegir restaurante")
            Button(onClick = onIrRestaurante) {
                Image(
                    painter = painterResource(Res.drawable.comida2),
                    contentDescription = "Foto comida",
                    modifier = Modifier
                        .size(100.dp) // Tamaño de la imagen
                        .clip(RectangleShape) // Corta la imagen en círculo si es una foto
                )
            }
        }
    }
}


