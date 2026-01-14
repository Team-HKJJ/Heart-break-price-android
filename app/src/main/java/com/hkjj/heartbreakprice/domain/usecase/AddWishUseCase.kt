package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.repository.WishRepository

class AddWishUseCase(
    private val wishRepository: WishRepository
) {
    suspend operator fun invoke(wishProduct: WishProduct): Result<Unit, Exception>{
        return try {
            wishRepository.addToWish(wishProduct)
            Result.Success(Unit)
        } catch (e: Exception){
            Result.Error(e)
        }
    }
}