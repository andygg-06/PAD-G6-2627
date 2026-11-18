package com.pad.dishmatch.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import com.pad.dishmatch.MainActivity

// Clave del dato que viaja en el Intent hasta MainActivity
const val EXTRA_TO_SCREEN = "toScreen"

class NotificationWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "Recordatorio"
        val message = inputData.getString("message") ?: "¡Vuelve a la aplicación!"
        val toScreen = inputData.getString("route")

        showNotification(title, message, toScreen)

        return Result.success()
    }

    fun createNotificationChannel(channelId: String, channelName: String) {
        // Sin canal la notificación no aparece en Android 8 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            applicationContext
                .getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String, toScreen: String?) {
        val channelId = "CHANNEL_ID_TEST"
        createNotificationChannel(channelId, "Work Manager Notification")

        val notificationId = System.currentTimeMillis().toInt()

        // Intent explícito: llamamos directamente a MainActivity
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_TO_SCREEN, toScreen)
        }

        // Quien abre la actividad al pulsar la notificación es Android,
        // no nuestro código, por eso hace falta un PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        applicationContext
            .getSystemService(NotificationManager::class.java)
            ?.notify(notificationId, notification)
    }
}
