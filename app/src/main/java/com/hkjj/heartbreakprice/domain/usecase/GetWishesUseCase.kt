package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.repository.WishRepository

class GetWishesUseCase(
    private val repository: WishRepository
) {
    suspend operator fun invoke(): Result<List<WishProduct>, Exception> {
        return try {
            Result.Success(repository.getWishes())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}