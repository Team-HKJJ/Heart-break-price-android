package com.hkjj.heartbreakprice.data.data_source.remote

import android.util.Log
import com.hkjj.heartbreakprice.BuildConfig
import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.data_source.remote.api.NaverShoppingApi
import com.hkjj.heartbreakprice.data.dto.ProductDto

class RemoteProductDataSourceImpl(
    private val api: NaverShoppingApi
) : ProductDataSource {

    private val clientId = BuildConfig.NAVER_CLIENT_ID
    private val clientSecret = BuildConfig.NAVER_CLIENT_SECRET

    override suspend fun getProducts(query: String): ApiResponse<List<ProductDto>> {
        return try {
            val response = api.searchProducts(clientId, clientSecret, query)
            if (response.isSuccessful) {
                val rawProducts = response.body()?.items ?: emptyList()
                val products = rawProducts.map { it.copy(title = removeHtmlTags(it.title)) }
                Log.d("RemoteProductDataSource", "Fetch success: ${products.size} products found")
                ApiResponse.Success(
                    statusCode = response.code(),
                    headers = response.headers().toMultimap(),
                    body = products
                )
            } else {
                val errorMsg = response.errorBody()?.string()
                Log.e("RemoteProductDataSource", "Fetch failed: Status ${response.code()}, Error: $errorMsg")
                ApiResponse.Failure(
                    statusCode = response.code(),
                    headers = response.headers().toMultimap(),
                    errorBody = errorMsg
                )
            }
        } catch (e: Exception) {
            Log.e("RemoteProductDataSource", "Network/Parsing error: ${e.message}", e)
            ApiResponse.NetworkError(e)
        }
    }

    private fun removeHtmlTags(text: String?): String? {
        if (text == null) return null
        return text.replace(Regex("<[^>]*>"), "")
            .replace("&quot;", "\"")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&amp;", "&")
            .replace("&apos;", "'")
            .replace("&nbsp;", " ")
    }
}
