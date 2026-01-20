package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.mapper.toDomain
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {

    override suspend fun getProducts(query: String): List<Product> {
        return when (val response = dataSource.getProducts(query)) {
            is ApiResponse.Success -> {
                response.body.map { it.toDomain() }
            }
            is ApiResponse.Failure, is ApiResponse.NetworkError -> {
                // Mock 데이터 환경이거나 에러 발생 시 빈 리스트 반환
                emptyList()
            }
        }
    }
}