package com.pad.dishmatch.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object Notification {

    // toScreen: pantalla que se abrirá al pulsar la notificación
    fun scheduleNotification(
        context: Context,
        title: String,
        message: String,
        delaySeconds: Long,
        toScreen: String = "principal"
    ) {
        val data = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .putString("route", toScreen)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
