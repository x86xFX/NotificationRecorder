package me.theek.nr.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.theek.nr.R
import me.theek.nr.presentation.viewmodel.NotificationDetailedViewModel
import me.theek.nr.presentation.viewmodel.UiState
import me.theek.nr.ui.theme.NotificationRecorderTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailedScreen(
    notificationId: Int?,
    onNavigateBackClicked: () -> Unit
) {
    if (notificationId == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.something_went_wrong))
            Button(onClick = onNavigateBackClicked) {
                Text(text = stringResource(R.string.go_back))
            }
        }
    } else {
        val viewModel = hiltViewModel<NotificationDetailedViewModel, NotificationDetailedViewModel.NotificationDetailedViewModelFactory> { factory ->
            factory.create(notificationId)
        }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (uiState) {
            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val postNotification = (uiState as UiState.Success).postNotification

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .navigationBarsPadding()
                        .statusBarsPadding(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = postNotification.sender ?: "Unknown")
                            },
                            actions = {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.DateRange,
                                        contentDescription = stringResource(R.string.date_range)
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = onNavigateBackClicked) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                        contentDescription = stringResource(R.string.navigate_back)
                                    )
                                }
                            }
                        )
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 15.dp
                                    )
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                    .border(
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(18.dp)
                                    )
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(100.dp),
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = stringResource(R.string.sender_s_image)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 10.dp,
                                            alignment = Alignment.CenterHorizontally
                                        )
                                    ) {
                                        Text(
                                            text = "Sender:",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Light,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )

                                        Text(
                                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                                            text = postNotification.sender ?: "Unknown",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Light,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 10.dp,
                                            alignment = Alignment.CenterHorizontally
                                        )
                                    ) {
                                        Text(
                                            text = "Package:",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Light,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )

                                        Text(
                                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                                            text = postNotification.packageName ?: "Unknown",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Light,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(space = 5.dp, alignment = Alignment.End)
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(postNotification.notificationIcon)
                                                .build(),
                                            contentDescription = stringResource(R.string.notification_app_icon),
                                            colorFilter = ColorFilter.tint(if (postNotification.colorCode == null) MaterialTheme.colorScheme.onBackground else Color(postNotification.colorCode)),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                        Text(
                                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                                            text = "Messaging",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Light,
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NotificationDetailedScreenPreview() {
    NotificationRecorderTheme {
        NotificationDetailedScreen(
            notificationId = 1,
            onNavigateBackClicked = {}
        )
    }
}