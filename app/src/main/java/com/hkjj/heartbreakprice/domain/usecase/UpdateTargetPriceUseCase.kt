package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.repository.WishRepository

class UpdateTargetPriceUseCase(
    private val wishRepository: WishRepository
) {
    suspend operator fun invoke(productId: String, targetPrice: Int) {
        wishRepository.updateTargetPrice(productId, targetPrice)
    }
}
