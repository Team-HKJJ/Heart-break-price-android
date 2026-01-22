package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.repository.ProductRepository
import com.hkjj.heartbreakprice.core.Result

class GetSearchedProductUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(query: String): Result<List<Product>, Exception> {
        return try {
            val products = repository.getProducts(query)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}