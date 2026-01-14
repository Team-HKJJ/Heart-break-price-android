package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProducts(query: String): List<Product> {
        return dataSource.getAllProducts().filter { it.name.contains(query, ignoreCase = true) }
    }
}