package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.repository.ProductRepository
import com.hkjj.heartbreakprice.core.network.ApiResponse

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProducts(query: String): List<Product> {
        // Issue 2에서 상세 구현 예정. 
        // 현재는 DataSource의 변경된 메서드 호출 구조만 반영
        val result = dataSource.getProducts(query)
        return emptyList()
    }
}