package me.theek.nr.data.repository

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.theek.nr.data.local.dao.NotificationDao
import me.theek.nr.data.mapper.toNotificationEntity
import me.theek.nr.data.mapper.toPostNotification
import me.theek.nr.domain.PostNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao,
    @ApplicationContext private val context: Context
) {

    fun checkNotificationListenerPermission(): Flow<Boolean> {
        val grantedApps = NotificationManagerCompat.getEnabledListenerPackages(context)
        return flow {
            delay(3000)
            emit(grantedApps.contains(context.packageName))
        }
    }

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

    fun getNotificationById(id: Int): Flow<PostNotification> {
        return notificationDao
            .getNotificationById(id = id)
            .map { it.toPostNotification() }
    }

}