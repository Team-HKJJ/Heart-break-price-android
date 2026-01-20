package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import com.hkjj.heartbreakprice.core.Result

class GetNotificationHistoryUseCase(
    private val notificationHistoryRepository: NotificationHistoryRepository
) {
    suspend operator fun invoke(): Result<List<Notification>, Exception> {
        return try {
            val notificationHistories = notificationHistoryRepository.getAllNotificationHistories()
            Result.Success(notificationHistories)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}