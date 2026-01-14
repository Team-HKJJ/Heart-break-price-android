package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.model.WishProduct

interface WishRepository {
    suspend fun getWishes(): Result<List<WishProduct>, Exception>
    suspend fun addToWish(wishProduct: WishProduct)
    suspend fun removeFromWishes(productId: String)
    suspend fun updateTargetPrice(productId: String, targetPrice: Int)
}