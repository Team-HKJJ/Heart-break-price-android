package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationHistoryRepositoryImpl(
    private val notificationHistoryDataSource: NotificationHistoryDataSource
) : NotificationHistoryRepository {
    override fun getAllNotificationHistories(): Flow<List<Notification>> {
        return notificationHistoryDataSource.getAllNotificationHistories()
    }

    override fun getUnreadNotificationHistories(): Flow<List<Notification>> {
        return notificationHistoryDataSource.getAllNotificationHistories()
            .map { notifications -> notifications.filter { !it.isRead } }
    }

    override suspend fun readAsMarkNotification(notificationId: String) {
        return notificationHistoryDataSource.readAsMarkNotification(notificationId)
    }

    override suspend fun deleteNotification(notificationId: String) {
        return notificationHistoryDataSource.deleteNotification(notificationId)
    }
}