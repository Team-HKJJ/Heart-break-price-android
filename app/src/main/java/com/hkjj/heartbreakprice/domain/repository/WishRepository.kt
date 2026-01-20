package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.WishProduct
import kotlinx.coroutines.flow.Flow

interface WishRepository {
    fun getWishes(): Flow<List<WishProduct>>
    suspend fun addToWish(wishProduct: WishProduct)
    suspend fun removeFromWishes(productId: String)
    suspend fun updateTargetPrice(productId: String, targetPrice: Int)
}