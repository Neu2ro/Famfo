package com.example.famfo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.google.firebase.database.core.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notifyChannel"
const val channelName = "Famfo"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Generate the notification
    // Attach the notification created with the custom layout
    // Show the notification
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification != null) {
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.famfo", R.layout.notification)
        remoteView.setTextViewText(R.id.notifyTitle, title)
        remoteView.setTextViewText(R.id.notifydesc, message)
        remoteView.setImageViewResource(R.id.appLogo, R.drawable.appicon)
        return remoteView
    }
    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, ApplicationSwitch::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        // Channel id, channel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.appicon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 100)) // Time for relax and vibrate
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title, message))
        val notificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }
}