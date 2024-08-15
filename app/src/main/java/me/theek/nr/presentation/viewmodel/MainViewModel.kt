package me.theek.nr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.theek.nr.data.repository.NotificationRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val notificationRepository: NotificationRepository) : ViewModel() {

    private val _permissionState: MutableStateFlow<PermissionUiState> = MutableStateFlow(PermissionUiState.Loading)
    val permissionState: StateFlow<PermissionUiState> = _permissionState.asStateFlow()

    fun checkPermission() {
        viewModelScope.launch {
            _permissionState.value = PermissionUiState.Loading
            notificationRepository.checkNotificationListenerPermission().collect { isGranted ->
                if (isGranted) {
                    _permissionState.value = PermissionUiState.PermissionGranted
                } else {
                    _permissionState.value = PermissionUiState.PermissionDenied
                }
            }
        }
    }
}

sealed interface PermissionUiState {
    data object Loading : PermissionUiState
    data object PermissionDenied : PermissionUiState
    data object PermissionGranted : PermissionUiState
}