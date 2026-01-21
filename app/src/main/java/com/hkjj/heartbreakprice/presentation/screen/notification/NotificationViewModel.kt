package com.hkjj.heartbreakprice.presentation.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.usecase.DeleteFcmTokenUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteNotificationUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetNotificationHistoryUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetUserUseCase
import com.hkjj.heartbreakprice.domain.usecase.ReadAsMarkNotificationUseCase
import com.hkjj.heartbreakprice.domain.usecase.UpdateFcmTokenUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationHistoryUseCase: GetNotificationHistoryUseCase,
    private val readAsMarkNotificationUseCase: ReadAsMarkNotificationUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
    private val deleteFcmTokenUseCase: DeleteFcmTokenUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<NotificationEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeNotificationHistories()
        fetchPushStatus()
    }

    fun onAction(action: NotificationAction) {
        when (action) {
            is NotificationAction.MarkAsRead -> markAsRead(action.id)
            NotificationAction.MarkAllAsRead -> markAllAsRead()
            is NotificationAction.DeleteNotification -> deleteNotification(action.id)
            is NotificationAction.TogglePushNotification -> togglePushNotification(action.isEnabled)
        }
    }

    private fun observeNotificationHistories() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            getNotificationHistoryUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            errorMessage = e.message,
                            isLoading = false
                        )
                    }
                    _event.send(NotificationEvent.ShowError(e.message ?: "Unknown error"))
                }
                .collect { notifications ->
                    _uiState.update {
                        it.copy(
                            notifications = notifications,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun fetchPushStatus() {
        viewModelScope.launch {
            val result = getUserUseCase()
            if (result is Result.Success) {
                val user = result.data
                _uiState.update {
                    it.copy(isPushEnabled = user.fcmToken.isNotEmpty())
                }
            }
        }
    }

    private fun togglePushNotification(isEnabled: Boolean) {
        viewModelScope.launch {
            // Optimistic update
            _uiState.update { it.copy(isPushEnabled = isEnabled) }

            val result = if (isEnabled) {
                updateFcmTokenUseCase()
            } else {
                deleteFcmTokenUseCase()
            }

            if (result is Result.Error) {
                // Revert on error
                _uiState.update { it.copy(isPushEnabled = !isEnabled) }
                _event.send(NotificationEvent.ShowError(result.error.message ?: "설정 변경에 실패했습니다."))
            }
        }
    }

    private fun markAsRead(id: String) {
        viewModelScope.launch {
            val result = readAsMarkNotificationUseCase(id)
            when (result) {
                is Result.Success -> {
                    // No need for manual update as flow will trigger
                }
                is Result.Error -> {
                    _event.send(NotificationEvent.ShowError(result.error.message ?: "Failed to mark as read"))
                }
            }
        }
    }

    private fun markAllAsRead() {
        val unreadNotifications = _uiState.value.notifications.filter { !it.isRead }
        if (unreadNotifications.isEmpty()) return

        viewModelScope.launch {
            // Since we don't have a bulk update use case, we'll iterate.
            // In a real app, this should be a bulk API call.
            var hasError = false
            unreadNotifications.forEach { notification ->
                val result = readAsMarkNotificationUseCase(notification.id)
                if (result is Result.Error) {
                    hasError = true
                }
            }

            if (hasError) {
                _event.send(NotificationEvent.ShowError("Failed to mark some notifications as read"))
            }
            // Flow will update UI
        }
    }

    private fun deleteNotification(id: String) {
        viewModelScope.launch {
            val deletedNotification = _uiState.value.notifications.find { it.id == id }

            // Optimistically remove from UI
            _uiState.update { state ->
                state.copy(
                    notifications = state.notifications.filter { it.id != id }
                )
            }

            // Perform deletion in backend
            val result = deleteNotificationUseCase(id)
            if (result is Result.Error) {
                // Revert on error
                if (deletedNotification != null) {
                    _uiState.update { state ->
                        // Add back at the correct position or just add to top/bottom?
                        // Since flow is sorted by timestamp, maybe just adding it back and sorting?
                        // Or just appending and letting next flow emission fix order.
                        // Simple append:
                        state.copy(notifications = (state.notifications + deletedNotification).sortedByDescending { it.timestamp })
                    }
                }
                _event.send(NotificationEvent.ShowError(result.error.message ?: "알림 삭제에 실패했습니다."))
            }
        }
    }
}
