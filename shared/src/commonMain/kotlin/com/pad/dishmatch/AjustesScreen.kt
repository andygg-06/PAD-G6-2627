package com.pad.dishmatch

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AjustesScreen(onVolverClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ajustes")
        Button( onClick = onVolverClick) {
            Text("Volver")
        }
    }
}