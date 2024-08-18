package me.theek.nr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.theek.nr.data.local.dao.NotificationDao
import me.theek.nr.data.local.entity.NotificationEntity

@Database(
    entities = [NotificationEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(BitmapConverts::class)
abstract class PostNotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}