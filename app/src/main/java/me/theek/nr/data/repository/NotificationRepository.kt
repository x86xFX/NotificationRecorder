package me.theek.nr.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.theek.nr.data.local.dao.NotificationDao
import me.theek.nr.data.mapper.toNotificationEntity
import me.theek.nr.data.mapper.toPostNotification
import me.theek.nr.domain.PostNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(private val notificationDao: NotificationDao) {

    fun addPostNotification(postNotification: PostNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            notificationDao.insertNotification(
                notificationEntity = postNotification.toNotificationEntity()
            )
        }
    }

    fun getAllPostNotifications(): Flow<List<PostNotification>> {
        return notificationDao
            .getAllNotifications()
            .map { notificationEntities ->
                notificationEntities.map {
                    it.toPostNotification()
                }
            }
    }

}