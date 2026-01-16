package com.hkjj.heartbreakprice.data.data_source.remote

import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.dto.ProductDto

class MockProductDataSourceImpl : ProductDataSource {

    override suspend fun getProducts(query: String): ApiResponse<List<ProductDto>> {
        return ApiResponse.Success(
            statusCode = 200,
            headers = emptyMap(),
            body = mockProductDtos.filter { it.title.contains(query, ignoreCase = true) }
        )
    }

    private val mockProductDtos = listOf(
        ProductDto(
            productId = "1",
            title = "갤럭시 S24 울트라 256GB",
            link = "",
            image = "https://images.unsplash.com/photo-1676173646307-d050e097d181?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHBob25lJTIwdGVjaG5vbG9neXxlbnwxfHx8fDE3NjgyOTQ5MzV8MA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "1299000",
            hprice = "",
            mallName = "G마켓",
            maker = "삼성전자",
            brand = "삼성",
            category1 = "디지털/가전",
            category2 = "주변기기",
            category3 = "스마트폰",
            category4 = ""
        ),
        ProductDto(
            productId = "2",
            title = "MacBook Pro 14인치 M3 Pro",
            link = "",
            image = "https://images.unsplash.com/photo-1511385348-a52b4a160dc2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXB0b3AlMjBjb21wdXRlcnxlbnwxfHx8fDE3NjgyOTYzMjN8MA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "2890000",
            hprice = "",
            mallName = "쿠팡",
            maker = "Apple",
            brand = "Apple",
            category1 = "디지털/가전",
            category2 = "노트북",
            category3 = "MacBook",
            category4 = ""
        )
    )
}
