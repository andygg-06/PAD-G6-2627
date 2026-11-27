package com.pad.dishmatch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pad.dishmatch.features.calendario.domain.Cita
import dishmatch.shared.generated.resources.Res
import dishmatch.shared.generated.resources.ic_ajustes
import dishmatch.shared.generated.resources.ic_calendario
import dishmatch.shared.generated.resources.ic_mapa
import dishmatch.shared.generated.resources.ic_perfil
import dishmatch.shared.generated.resources.ic_personas
import dishmatch.shared.generated.resources.ic_restaurante
import dishmatch.shared.generated.resources.logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PaginaPrincipal(
    proximaCita: Cita? = null,
    onIrCalendario: () -> Unit,
    onIrMapa: () -> Unit,
    onIrAjustes: () -> Unit,
    onIrPerfil: () -> Unit,
    onIrConocer: () -> Unit,
    onIrRestaurante: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        // Logotipo, nombre y acceso al perfil
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "DishMatch",
                modifier = Modifier.height(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "DishMatch",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .clickable(onClick = onIrPerfil),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_perfil),
                    contentDescription = "Mi perfil",
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tarjeta de próxima cita (solo aparece si hay un plan agendado)
        if (proximaCita != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onIrCalendario),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📅", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TU PRÓXIMO PLAN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Día ${proximaCita.diaMes} a las ${proximaCita.hora}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Con ${proximaCita.personaNombre} en ${proximaCita.restauranteNombre}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Text(
                        text = "Ver →",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(
            text = "¿Con quién comes hoy?",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Las dos acciones principales de la aplicacion
        AccionPrincipal(
            titulo = "Conocer a alguien",
            descripcion = "Personas con tus mismos gustos, cerca de ti",
            imagen = Res.drawable.ic_personas,
            onClick = onIrConocer
        )

        Spacer(modifier = Modifier.height(14.dp))

        AccionPrincipal(
            titulo = "Elegir restaurante",
            descripcion = "Sitios que encajan con tu carta de preferencias",
            imagen = Res.drawable.ic_restaurante,
            onClick = onIrRestaurante
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "ACCESOS RÁPIDOS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AccesoRapido(
                modifier = Modifier.weight(1f),
                titulo = "Calendario",
                imagen = Res.drawable.ic_calendario,
                onClick = onIrCalendario
            )
            AccesoRapido(
                modifier = Modifier.weight(1f),
                titulo = "Mapa",
                imagen = Res.drawable.ic_mapa,
                onClick = onIrMapa
            )
            AccesoRapido(
                modifier = Modifier.weight(1f),
                titulo = "Ajustes",
                imagen = Res.drawable.ic_ajustes,
                onClick = onIrAjustes
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/** Tarjeta grande: icono a la izquierda, titulo y explicacion a la derecha */
@Composable
private fun AccionPrincipal(
    titulo: String,
    descripcion: String,
    imagen: DrawableResource,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imagen),
                contentDescription = null,
                modifier = Modifier.size(52.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = descripcion,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/** Tarjeta pequena de la fila de abajo */
@Composable
private fun AccesoRapido(
    modifier: Modifier = Modifier,
    titulo: String,
    imagen: DrawableResource,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imagen),
                contentDescription = null,
                modifier = Modifier.size(34.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
