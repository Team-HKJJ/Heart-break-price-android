package com.hkjj.heartbreakprice.presentation.screen.notification

sealed interface NotificationEvent {
    data class ShowError(val message: String) : NotificationEvent
}
