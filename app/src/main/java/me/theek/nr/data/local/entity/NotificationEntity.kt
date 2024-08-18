package me.theek.nr.data.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "notification_title") val title: String?,
    @ColumnInfo(name = "notification_content") val content: String?,
    @ColumnInfo(name = "notification_sender") val sender: String?,
    @ColumnInfo(name = "app_name") val appName: String?,
    @ColumnInfo(name = "app_package_name") val packageName: String?,
    @ColumnInfo(name = "app_icon") val appIcon: Bitmap?,
    @ColumnInfo(name = "notification_small_icon") val notificationIcon: Bitmap?,
    @ColumnInfo(name = "notification_color_code") val colorCode: Int?,
    @ColumnInfo(name = "notification_received_at") val receivedTime: Long?
)