package me.theek.nr.domain

import android.graphics.Bitmap

data class PostNotification(
    val id: Int,
    val title: String?,
    val content: String?,
    val sender: String?,
    val appName: String?,
    val packageName: String?,
    val receivedTime: Long?,
    val notificationIcon: Bitmap?,
    val appIcon: Bitmap?,
    val colorCode: Int?
)