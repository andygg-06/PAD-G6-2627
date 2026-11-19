package com.pad.dishmatch.features.autentificacion.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegistroScreen(
    onRegistrar: (nombre: String, email: String, password: String, consentimientoSalud: Boolean) -> String?,
    onVolverLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repetir by remember { mutableStateOf("") }
    var consentimiento by remember { mutableStateOf(false) }   // sin marcar por defecto
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedButton(onClick = onVolverLogin) { Text("← Volver") }
        Text("Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") },
            singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(email, { email = it }, label = { Text("Correo") },
            singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(password, { password = it }, label = { Text("Contraseña (mín. 8)") },
            singleLine = true, visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(repetir, { repetir = it }, label = { Text("Repite la contraseña") },
            singleLine = true, visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())

        // Consentimiento explícito, separado y opcional (RGPD art. 9.2.a)
        Row(
            Modifier.fillMaxWidth().clickable { consentimiento = !consentimiento },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = consentimiento, onCheckedChange = { consentimiento = it })
            Text(
                "Acepto expresamente que DishMatch guarde mis datos de dieta y alergias " +
                        "para recomendarme restaurantes y personas compatibles. Es opcional y " +
                        "puedo retirarlo cuando quiera.",
                fontSize = 13.sp
            )
        }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = {
                error = when {
                    nombre.isBlank() -> "Escribe tu nombre"
                    password != repetir -> "Las contraseñas no coinciden"
                    else -> onRegistrar(nombre.trim(), email, password, consentimiento)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) { Text("Crear cuenta") }
    }
}