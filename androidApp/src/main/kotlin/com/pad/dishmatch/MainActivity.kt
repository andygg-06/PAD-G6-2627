package com.pad.dishmatch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.pad.dishmatch.notifications.EXTRA_TO_SCREEN
import com.pad.dishmatch.notifications.Notification

class MainActivity : ComponentActivity() {

    // Pantalla que hay que abrir cuando se entra desde una notificación
    private var toScreen by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Si la app estaba cerrada, el Intent de la notificación llega aquí
        toScreen = pantallaDesdeIntent(intent)

        setContent {
            App(toScreen = toScreen)
        }
    }

    // Si la app ya estaba abierta, gracias a singleTop llega aquí
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        toScreen = pantallaDesdeIntent(intent)
    }

    override fun onStop() {
        super.onStop()
        Notification.scheduleNotification(
            context = applicationContext,
            title = "DishMatch",
            message = "¿Ya sabes dónde comerás? Mira tu calendario",
            delaySeconds = 5,
            toScreen = "calendario"
        )
    }

    private fun pantallaDesdeIntent(intent: Intent?): String? =
        intent?.getStringExtra(EXTRA_TO_SCREEN)
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
