package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.repository.WishRepository

class DeleteWishUseCase(
    private val wishRepository: WishRepository,
) {
    suspend operator fun invoke(productId: String): Result<Unit, Exception> {
        return try {
            wishRepository.removeFromWishes(productId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}