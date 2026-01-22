package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(query: String): List<Product>
}