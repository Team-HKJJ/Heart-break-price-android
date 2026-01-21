package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationHistoryDataSource {
    fun getAllNotificationHistories(): Flow<List<Notification>>

    suspend fun readAsMarkNotification(id: String)

    suspend fun deleteNotification(id: String)
}