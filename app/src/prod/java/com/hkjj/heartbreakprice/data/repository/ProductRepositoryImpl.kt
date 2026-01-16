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
            is ApiResponse.Failure -> {
                // TODO: 에러 발생 시 로깅 또는 커스텀 예외 처리 필요
                emptyList()
            }
            is ApiResponse.NetworkError -> {
                // TODO: 네트워크 에러 발생 시 처리
                emptyList()
            }
        }
    }
}