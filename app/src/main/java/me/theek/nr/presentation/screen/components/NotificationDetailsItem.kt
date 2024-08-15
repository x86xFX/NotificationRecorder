package me.theek.nr.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import me.theek.nr.ui.theme.NotificationRecorderTheme
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun NotificationDetailsItem(
    id: Int,
    title: String?,
    description: String?,
    time: Long?,
    appIconBase64String: String?,
    colorCode: Int?,
    onNotificationClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageByteArray = if (appIconBase64String == null) {
        null
    } else {
        Base64.decode(appIconBase64String)
    }

    Row(
        modifier = modifier
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .background(MaterialTheme.colorScheme.background)
            .clickable { onNotificationClicked(id) }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageByteArray)
                .build(),
            contentDescription = stringResource(R.string.notification_app_icon),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(if (colorCode == null) MaterialTheme.colorScheme.onBackground else Color(colorCode)),
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title ?: "Unknown",
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description ?: "Unknown",
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                softWrap = true
            )

            Text(
                text = time?.toString() ?: "Unknown",
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                fontSize = MaterialTheme.typography.labelSmall.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationDetailsItemPreview() {
    NotificationRecorderTheme {
        NotificationDetailsItem(
            id = 1,
            title = "Dilara sent a message",
            description = "Kala enna epai ithin. Udeta hari kaewada? Oka iwara wenne kiyata da danneth na",
            time = 342342643,
            appIconBase64String = null,
            colorCode = null,
            onNotificationClicked = {}
        )
    }
}