package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationHistoryRepository {
    fun getAllNotificationHistories(): Flow<List<Notification>>

    fun getUnreadNotificationHistories(): Flow<List<Notification>>

    suspend fun readAsMarkNotification(notificationId: String)

    suspend fun deleteNotification(notificationId: String)
}