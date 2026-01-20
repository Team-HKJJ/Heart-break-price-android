package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class DeleteFcmTokenUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, Exception> {
        TODO("firestore에 저장된 토큰 삭제 필요")
    }
}