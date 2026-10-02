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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dishmatch.shared.generated.resources.ajustes
import dishmatch.shared.generated.resources.mapa
import org.jetbrains.compose.resources.painterResource

@Composable

fun HomeScreen() {

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
            Button(onClick = { } ,
                    modifier = Modifier.size(72.dp)
            ) {
                Text("foto persona")
            }
            Text("Nombre")
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.7f) // Ocupa el 70% del ancho del contenedor
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
            Button(onClick = { }) {
                Image(
                    painter = painterResource(Res.drawable.calendario),
                    contentDescription = "Foto calendario",
                    modifier = Modifier
                        .size(24.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Calendario")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }) {
                Image(
                    painter = painterResource(Res.drawable.mapa),
                    contentDescription = "Foto mapa ",
                    modifier = Modifier
                        .size(24.dp) // Tamaño de la imagen
                        .clip(CircleShape) // Corta la imagen en círculo si es una foto
                )
            }
            Text("Mapa")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { }) {
                Image(
                    painter = painterResource(Res.drawable.ajustes),
                    contentDescription = "Foto ajustes",
                    modifier = Modifier
                        .size(24.dp) // Tamaño de la imagen
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
            Button(onClick = { }) {
                Text("foto personas")
            }
            Text("Elegir restaurante")
            Button(onClick = { }) {
                Text("foto comida")
            }
        }
    }
}