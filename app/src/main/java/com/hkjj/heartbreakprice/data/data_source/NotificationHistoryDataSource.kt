package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.domain.model.Notification

interface NotificationHistoryDataSource {
    suspend fun getAllNotificationHistories(): List<Notification>

    suspend fun readAsMarkNotification(id: String)
}