package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationHistoryUseCase(
    private val notificationHistoryRepository: NotificationHistoryRepository
) {
    operator fun invoke(): Flow<List<Notification>> {
        return notificationHistoryRepository.getAllNotificationHistories()
    }
}