package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        return try {
            try {
                authRepository.updateFcmToken("")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            authRepository.logout()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}