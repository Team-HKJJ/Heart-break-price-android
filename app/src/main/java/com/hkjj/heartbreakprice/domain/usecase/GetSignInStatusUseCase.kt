package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first

class GetSignInStatusUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean = authRepository.isSignIn.first()
}