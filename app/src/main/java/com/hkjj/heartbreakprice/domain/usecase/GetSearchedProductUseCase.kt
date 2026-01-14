package com.hkjj.heartbreakprice.domain.usecase

import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.repository.ProductRepository

class GetSearchedProductUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return runCatching { repository.getProducts(query) }
    }
}