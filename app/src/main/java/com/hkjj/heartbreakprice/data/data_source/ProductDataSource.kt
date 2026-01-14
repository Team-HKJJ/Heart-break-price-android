package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.domain.model.Product

interface ProductDataSource {
    suspend fun getAllProducts(): List<Product>
}