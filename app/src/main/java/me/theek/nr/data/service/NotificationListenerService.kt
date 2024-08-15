package me.theek.nr.data.service

import android.app.Notification
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.AndroidEntryPoint
import me.theek.nr.data.repository.NotificationRepository
import me.theek.nr.domain.PostNotification
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        val extras = sbn?.notification?.extras
        val extraMessage = extras?.getParcelableArray(Notification.EXTRA_MESSAGES, Bundle::class.java)

        var content: String? = null
        var sender: String? = null

        extraMessage?.forEach {
            // extras, sender_person, sender, text, time
            content = it.getCharSequence("text").toString()
            sender = it.getString("sender")
        }

        notificationRepository.addPostNotification(
            postNotification = PostNotification(
                id = 0,
                title = sbn?.notification?.tickerText?.toString(),
                content = content,
                sender = sender,
                packageName = sbn?.packageName,
                receivedTime = sbn?.postTime,
                appIconBase64String = sbn?.notification?.smallIcon?.loadDrawable(this)?.toBitmap().toBase64String(),
                colorCode = sbn?.notification?.color

            )
        )

        super.onNotificationPosted(sbn)
    }
}

@OptIn(ExperimentalEncodingApi::class)
private fun Bitmap?.toBase64String(): String? {
    if (this == null) {
        return null
    } else {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return Base64.encode(byteArrayOutputStream.toByteArray())
    }
}