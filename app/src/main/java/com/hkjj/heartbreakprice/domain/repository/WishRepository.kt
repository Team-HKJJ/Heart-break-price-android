package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.model.WishProduct

interface WishRepository {
    suspend fun getWishes(): List<WishProduct>
    suspend fun addToWish(product: Product)
    suspend fun removeFromWishes(productId: String)
    suspend fun updateTargetPrice(productId: String, targetPrice: Int)
}