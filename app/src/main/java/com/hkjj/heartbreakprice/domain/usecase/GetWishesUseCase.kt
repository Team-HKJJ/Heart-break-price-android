package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.repository.WishRepository

import kotlinx.coroutines.flow.Flow

class GetWishesUseCase(
    private val repository: WishRepository
) {
    operator fun invoke(): Flow<List<WishProduct>> {
        return repository.getWishes()
    }
}