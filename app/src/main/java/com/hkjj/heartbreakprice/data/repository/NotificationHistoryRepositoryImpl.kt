package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository

class NotificationHistoryRepositoryImpl(
    private val notificationHistoryDataSource: NotificationHistoryDataSource
) : NotificationHistoryRepository {
    override suspend fun getAllNotificationHistories(): List<Notification> {
        return notificationHistoryDataSource.getAllNotificationHistories()
    }

    override suspend fun getUnreadNotificationHistories(): List<Notification> {
        return notificationHistoryDataSource.getAllNotificationHistories().filter { !it.isRead }
    }

    override suspend fun readAsMarkNotification(notificationId: String) {
        return notificationHistoryDataSource.readAsMarkNotification(notificationId)
    }

    override suspend fun deleteNotification(notificationId: String) {
        return notificationHistoryDataSource.deleteNotification(notificationId)
    }
}