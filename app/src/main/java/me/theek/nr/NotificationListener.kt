package me.theek.nr

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint
import me.theek.nr.data.repository.NotificationRepository
import me.theek.nr.domain.PostNotification
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository


    /**
     * https://stackoverflow.com/questions/28047767/notificationlistenerservice-not-reading-text-of-stacked-notifications
     */

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val extras = sbn?.notification?.extras
        extras?.getString(Notification.EXTRA_MESSAGES)

        println("""
            ${sbn?.postTime}
            ${sbn?.notification?.tickerText}
            ${sbn?.notification?.color}
            ${sbn?.notification?.`when`}
            ${sbn?.notification?.smallIcon.toString()}
        """.trimIndent()
        )

        super.onNotificationPosted(sbn)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}