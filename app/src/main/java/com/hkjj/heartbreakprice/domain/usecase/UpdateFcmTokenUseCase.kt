package com.hkjj.heartbreakprice.domain.usecase

import android.util.Log
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.notification.NotificationProvider
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class UpdateFcmTokenUseCase(
    private val fcmProvider: NotificationProvider,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(passedToken: String? = null): Result<Unit, Exception> {
        return try {
            val token = passedToken ?: fcmProvider.getFcmToken()
            Log.d("FCM_LOG", "Update FCM Token: $token")

            if (token == null) {
                return Result.Error(Exception("FCM 토큰을 가져올 수 없습니다."))
            }

            authRepository.updateFcmToken(token)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}