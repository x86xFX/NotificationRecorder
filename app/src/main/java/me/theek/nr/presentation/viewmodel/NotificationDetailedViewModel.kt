package me.theek.nr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.theek.nr.data.repository.NotificationRepository
import me.theek.nr.domain.PostNotification

@HiltViewModel(assistedFactory = NotificationDetailedViewModel.NotificationDetailedViewModelFactory::class)
class NotificationDetailedViewModel @AssistedInject constructor(
    @Assisted val notificationId: Int,
    notificationRepository: NotificationRepository
) : ViewModel() {

    @AssistedFactory
    interface NotificationDetailedViewModelFactory {
        fun create(notificationId: Int): NotificationDetailedViewModel
    }

    val uiState: StateFlow<UiState> = notificationRepository.getNotificationById(id = notificationId)
        .map { UiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Loading
        )
}

sealed interface UiState {
    data object Loading : UiState
    data class Success(val postNotification: PostNotification): UiState
}