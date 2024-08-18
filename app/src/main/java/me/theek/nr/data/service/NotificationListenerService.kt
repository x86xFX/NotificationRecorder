package me.theek.nr.data.service

import android.app.Notification
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import dagger.hilt.android.AndroidEntryPoint
import me.theek.nr.data.repository.NotificationRepository
import me.theek.nr.domain.PostNotification
import javax.inject.Inject


@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {

        val extras = sbn?.notification?.extras
        val packageName = sbn?.packageName
        val packageManager = this.packageManager
        val extraMessage = extras?.getParcelableArray(Notification.EXTRA_MESSAGES, Bundle::class.java)

        var content: String? = null
        var sender: String? = null
        var appName: String? = null
        var appIcon: Bitmap? = null

        extraMessage?.forEach {
            // extras, sender_person, sender, text, time
            content = it.getCharSequence("text").toString()
            sender = it.getString("sender")
        }

        packageName?.let {
            val appInfo = packageManager.getApplicationInfo(it, 0)
            appName = packageManager.getApplicationLabel(appInfo).toString()
            appIcon = packageManager.getApplicationIcon(appInfo).toBitmapOrNull()
        }

        notificationRepository.addPostNotification(
            postNotification = PostNotification(
                id = 0,
                title = sbn?.notification?.tickerText?.toString(),
                content = content,
                sender = sender,
                appName = appName,
                packageName = packageName,
                receivedTime = sbn?.postTime,
                notificationIcon = sbn?.notification?.smallIcon?.loadDrawable(this)?.toBitmap(),
                appIcon = appIcon,
                colorCode = sbn?.notification?.color
            )
        )

        super.onNotificationPosted(sbn)
    }
}