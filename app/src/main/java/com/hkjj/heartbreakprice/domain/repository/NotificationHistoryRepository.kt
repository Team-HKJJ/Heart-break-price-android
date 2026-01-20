package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.Notification

interface NotificationHistoryRepository {
    suspend fun getAllNotificationHistories(): List<Notification>

    suspend fun getUnreadNotificationHistories(): List<Notification>

    suspend fun readAsMarkNotification(notificationId: String)

    suspend fun deleteNotification(notificationId: String)
}