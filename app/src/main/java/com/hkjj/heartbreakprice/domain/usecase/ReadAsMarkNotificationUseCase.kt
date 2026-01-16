package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import com.hkjj.heartbreakprice.core.Result

class ReadAsMarkNotificationUseCase(
    private val notificationHistoryRepository: NotificationHistoryRepository
) {
    suspend operator fun invoke(notificationId: String): Result<Unit, Exception> {
        return try {
            notificationHistoryRepository.readAsMarkNotification(notificationId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}