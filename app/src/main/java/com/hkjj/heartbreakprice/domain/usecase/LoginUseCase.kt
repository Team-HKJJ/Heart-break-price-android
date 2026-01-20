package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit, Exception> {
        return authRepository.login(email, password)
    }
}