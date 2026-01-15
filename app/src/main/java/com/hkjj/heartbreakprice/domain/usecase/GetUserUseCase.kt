package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.User
import com.hkjj.heartbreakprice.domain.repository.AuthRepository

class GetUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User, Exception> {
        return try {
            Result.Success(authRepository.getUser())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}