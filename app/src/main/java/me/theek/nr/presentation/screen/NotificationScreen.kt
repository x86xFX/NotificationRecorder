package me.theek.nr.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.theek.nr.R
import me.theek.nr.presentation.screen.components.NotificationDetailsItem
import me.theek.nr.presentation.viewmodel.NotificationUiState
import me.theek.nr.presentation.viewmodel.NotificationViewModel

@Composable
fun NotificationScreen(
    onNotificationClicked: (Int) -> Unit,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {

    val notificationUiState by  notificationViewModel.notificationState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        when(val state = notificationUiState) {
            NotificationUiState.Loading -> {
                CircularProgressIndicator(strokeCap = StrokeCap.Butt)
            }
            is NotificationUiState.Success -> {
                val notifications = state.data

                if (notifications.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(space = 10.dp, alignment = Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = stringResource(R.string.notification_icon),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "No recorded notifications have been found yet",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = notifications,
                            key = { it.id }
                        ) { notification ->
                            NotificationDetailsItem(
                                postNotification = notification,
                                onNotificationClicked = onNotificationClicked
                            )
                        }
                    }
                }
            }
        }

    }
}