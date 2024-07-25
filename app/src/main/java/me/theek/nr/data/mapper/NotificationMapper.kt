package me.theek.nr.data.mapper

import me.theek.nr.data.local.entity.NotificationEntity
import me.theek.nr.domain.PostNotification

fun NotificationEntity.toPostNotification() : PostNotification {
    return PostNotification(
        title = title,
        content = content,
        packageName = packageName,
        receivedTime = receivedTime
    )
}

fun PostNotification.toNotificationEntity() : NotificationEntity {
    return NotificationEntity(
        id = 0,
        title = title,
        content = content,
        packageName = packageName,
        receivedTime = receivedTime
    )
}