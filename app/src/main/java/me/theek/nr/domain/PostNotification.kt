package me.theek.nr.domain

data class PostNotification(
    val id: Int,
    val title: String?,
    val content: String?,
    val sender: String?,
    val packageName: String?,
    val receivedTime: Long?,
    val appIconBase64String: String?,
    val colorCode: Int?
)