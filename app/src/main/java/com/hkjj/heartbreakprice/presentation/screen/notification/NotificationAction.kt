package com.hkjj.heartbreakprice.presentation.screen.notification

sealed interface NotificationAction {
    data class MarkAsRead(val id: String) : NotificationAction
    data object MarkAllAsRead : NotificationAction
    data class DeleteNotification(val id: String) : NotificationAction
}
