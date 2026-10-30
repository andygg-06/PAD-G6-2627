package com.pad.dishmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pad.dishmatch.notifications.Notification

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }

    override fun onStop() {
        super.onStop()
        Notification.scheduleNotification(
            context = this,
            title = "DishMatch",
            message = "¡Ya sabes donde comeras mañana, vena  descubrirlo!",
            delaySeconds = 5
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}