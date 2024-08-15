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
        receivedTime = receivedTime,
        appIconBase64String = appIconBase64String,
        colorCode = colorCode
    )
}

fun PostNotification.toNotificationEntity() : NotificationEntity {
    return NotificationEntity(
        title = title,
        content = content,
        sender = sender,
        packageName = packageName,
        receivedTime = receivedTime,
        appIconBase64String = appIconBase64String,
        colorCode = colorCode
    )
}