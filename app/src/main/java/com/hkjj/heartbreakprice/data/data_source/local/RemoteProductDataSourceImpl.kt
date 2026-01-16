package com.hkjj.heartbreakprice.data.data_source.local

import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.remote.api.NaverShoppingApi
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.dto.ProductDto

class RemoteProductDataSourceImpl(
    private val api: NaverShoppingApi
) : ProductDataSource {
    override suspend fun getProducts(query: String): ApiResponse<List<ProductDto>> {
        // TODO: 상세 구현 (api.searchProducts(..., query, ...) 호출 및 XML 파싱)
        return ApiResponse.Failure(501, emptyMap(), "Not Implemented Yet")
    }
}