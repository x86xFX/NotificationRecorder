package me.theek.nr.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.theek.nr.R
import me.theek.nr.domain.PostNotification
import me.theek.nr.ui.theme.NotificationRecorderTheme
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NotificationDetailsItem(
    postNotification: PostNotification,
    onNotificationClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val themedColor = if (postNotification.colorCode == null) MaterialTheme.colorScheme.onBackground else Color(postNotification.colorCode)

    Row(
        modifier = modifier
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = themedColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(MaterialTheme.colorScheme.background),
//            .clickable { onNotificationClicked(id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = postNotification.sender ?: "Unknown sender",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 12.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 12.dp
                            )
                        )
                        .border(
                            width = 1.dp,
                            color = themedColor,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 12.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 12.dp
                            )
                        )
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 5.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(postNotification.notificationIcon)
                            .build(),
                        contentDescription = stringResource(R.string.notification_app_icon),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(themedColor),
                        modifier = Modifier
                            .size(25.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Text(
                        text = postNotification.appName ?: "Unknown",
                        color = themedColor
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    text = postNotification.title ?: "Unknown",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = postNotification.content ?: "Unknown",
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    softWrap = true
                )

                Text(
                    text = postNotification.receivedTime?.toReadableTimeFormat() ?: "Unknown",
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                )
            }
        }
    }
}

private fun Long?.toReadableTimeFormat(): String? {
    return if (this == null) {
        null
    } else {
        return SimpleDateFormat.getDateTimeInstance().format(Date(this))
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationDetailsItemPreview() {
    NotificationRecorderTheme {
        NotificationDetailsItem(
            postNotification = PostNotification(
                id = 1,
                title = "XYZ sent a message",
                sender = "XYZ",
                content = "Android is a mobile operating system based on a modified version of the Linux kernel and other open-source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.",
                receivedTime = 342342643,
                notificationIcon = null,
                appIcon = null,
                colorCode = null,
                appName = "Whatsapp",
                packageName = "com.whatsapp"
            ),
            onNotificationClicked = {}
        )
    }
}