package com.hkjj.heartbreakprice.presentation.screen.notification

import com.hkjj.heartbreakprice.domain.model.Notification

data class NotificationUiState (
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)