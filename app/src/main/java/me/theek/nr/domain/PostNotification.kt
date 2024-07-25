package me.theek.nr.domain

data class PostNotification(
    val title: String?,
    val content: String?,
    val packageName: String?,
    val receivedTime: Long?
)