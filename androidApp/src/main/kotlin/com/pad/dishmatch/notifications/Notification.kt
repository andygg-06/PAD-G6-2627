package com.pad.dishmatch.notifications

import android.content.Context
import android.provider.ContactsContract

import java.util.concurrent.TimeUnit

object Notification {
    fun scheduleNotification(
        context: Context,
        title: String,
        message: String,
        delaySeconds: Long
    ) {
        val data = ContactsContract.Contacts.Data.Builder()
            .putString("title", title)
            .putString("message", message)

            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}