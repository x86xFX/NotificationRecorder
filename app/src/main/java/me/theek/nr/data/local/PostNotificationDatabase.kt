package me.theek.nr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.theek.nr.data.local.dao.NotificationDao
import me.theek.nr.data.local.entity.NotificationEntity

@Database(
    entities = [NotificationEntity::class],
    version = 1,
    exportSchema = true
)
abstract class PostNotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}