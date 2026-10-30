package com.pad.dishmatch.notifications

import android.content.Context
import androidx.work.WorkerParameters


class NotificationWorker(appContext: Context, workerParameters: WorkerParameters) :
        CoroutineWorker(appContext, workerParameters) {

        override suspend fun doWork(): Result {

            return Result.success()

        }

        fun createNotificationChannel(channelId: String, channelName: String) {


        }


        fun showNotification(title: String, message: String) {


        }
    }
