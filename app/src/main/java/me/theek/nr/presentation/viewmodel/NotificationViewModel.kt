package me.theek.nr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.theek.nr.data.repository.NotificationRepository
import me.theek.nr.domain.PostNotification
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(notificationRepository: NotificationRepository) : ViewModel() {

    val notificationState: StateFlow<NotificationUiState> = notificationRepository.getAllPostNotifications()
        .map { notifications ->
            NotificationUiState.Success(notifications)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotificationUiState.Loading
        )
}

sealed interface NotificationUiState {
    data object Loading : NotificationUiState
    data class Success(val data: List<PostNotification>) : NotificationUiState
}