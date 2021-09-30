package com.yagmurerdogan.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Yağmur ERDOĞAN on 30 September 2021
 */
class NotifWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    private fun createNotification() {

        val builder: NotificationCompat.Builder

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)

        val contentToGo = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelId"
            val channelName = "channelName"
            val channelIntroduction = "channelIntroduction"
            val channelPriority = NotificationManager.IMPORTANCE_HIGH

            var channel: NotificationChannel? = notificationManager
                .getNotificationChannel(channelId)

            if (channel == null) {
                channel = NotificationChannel(channelId, channelName, channelPriority)
                channel.description = channelIntroduction
                notificationManager.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(applicationContext, channelId)
            builder
                .setContentTitle("Title")
                .setContentText("Content Text")
                .setSmallIcon(R.drawable.ic_heart)
                .setAutoCancel(true)
                .setContentIntent(contentToGo)

        } else {
            builder = NotificationCompat.Builder(applicationContext)
            builder
                .setContentTitle("Title")
                .setContentText("Content Text")
                .setSmallIcon(R.drawable.ic_heart)
                .setAutoCancel(true)
                .setContentIntent(contentToGo)
                .priority = Notification.PRIORITY_HIGH
        }
        notificationManager.notify(1, builder.build())
    }
}