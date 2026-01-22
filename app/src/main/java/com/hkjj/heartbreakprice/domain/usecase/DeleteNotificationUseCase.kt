package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository

class DeleteNotificationUseCase(
    private val repository: NotificationHistoryRepository
) {
    suspend operator fun invoke(notificationId: String): Result<Unit, Exception> {
        return try {
            repository.deleteNotification(notificationId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
