package me.theek.nr.data.mapper

import me.theek.nr.data.local.entity.NotificationEntity
import me.theek.nr.domain.PostNotification

fun NotificationEntity.toPostNotification() : PostNotification {
    return PostNotification(
        id = id,
        title = title,
        content = content,
        sender = sender,
        packageName = packageName,
        appName = appName,
        receivedTime = receivedTime,
        appIcon = appIcon,
        notificationIcon = notificationIcon,
        colorCode = colorCode
    )
}

fun PostNotification.toNotificationEntity() : NotificationEntity {
    return NotificationEntity(
        title = title,
        content = content,
        sender = sender,
        packageName = packageName,
        appName = appName,
        receivedTime = receivedTime,
        appIcon = appIcon,
        notificationIcon = notificationIcon,
        colorCode = colorCode
    )
}