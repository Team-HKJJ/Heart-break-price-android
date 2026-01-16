package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.dto.ProductDto

interface ProductDataSource {
    suspend fun getProducts(query: String): ApiResponse<List<ProductDto>>
}