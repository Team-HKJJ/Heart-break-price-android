package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class DeleteFcmTokenUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        authRepository.updateFcmToken("")
        return Result.Success(Unit)
    }
}