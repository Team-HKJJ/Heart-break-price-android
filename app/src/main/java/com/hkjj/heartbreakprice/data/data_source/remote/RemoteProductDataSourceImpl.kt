package com.hkjj.heartbreakprice.data.data_source.remote

import android.util.Xml
import com.hkjj.heartbreakprice.BuildConfig
import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.data_source.remote.api.NaverShoppingApi
import com.hkjj.heartbreakprice.data.dto.ProductDto
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader

class RemoteProductDataSourceImpl(
    private val api: NaverShoppingApi
) : ProductDataSource {

    private val clientId = BuildConfig.NAVER_CLIENT_ID
    private val clientSecret = BuildConfig.NAVER_CLIENT_SECRET

    override suspend fun getProducts(query: String): ApiResponse<List<ProductDto>> {
        return try {
            val response = api.searchProducts(clientId, clientSecret, query)
            if (response.isSuccessful) {
                val xmlBody = response.body() ?: ""
                val products = parseXml(xmlBody)
                ApiResponse.Success(
                    statusCode = response.code(),
                    headers = response.headers().toMultimap(),
                    body = products
                )
            } else {
                ApiResponse.Failure(
                    statusCode = response.code(),
                    headers = response.headers().toMultimap(),
                    errorBody = response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            ApiResponse.NetworkError(e)
        }
    }

    private fun parseXml(xml: String): List<ProductDto> {
        val products = mutableListOf<ProductDto>()
        try {
            val parser = Xml.newPullParser()
            parser.setInput(StringReader(xml))

            var eventType = parser.eventType
            var currentItem: MutableMap<String, String>? = null
            var currentTag = ""

            val targetTags = setOf(
                "productId", "title", "link", "image", "lprice", "hprice",
                "mallName", "maker", "brand", "category1", "category2", "category3", "category4"
            )

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name
                        if (tagName == "item") {
                            currentItem = mutableMapOf()
                        } else if (currentItem != null && targetTags.contains(tagName)) {
                            currentTag = tagName
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (currentItem != null && currentTag.isNotEmpty()) {
                            val text = parser.text
                            currentItem[currentTag] = (currentItem[currentTag] ?: "") + text
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        val tagName = parser.name
                        if (tagName == "item" && currentItem != null) {
                            products.add(mapToProductDto(currentItem))
                            currentItem = null
                        } else if (tagName == currentTag) {
                            if (currentTag == "title") {
                                currentItem?.get("title")?.let {
                                    currentItem.put("title", removeHtmlTags(it))
                                }
                            }
                            currentTag = ""
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            // 파싱 중 에러 발생 시 로그를 남기거나 빈 리스트 반환 (호출부에서 에러 처리 가능하도록 설계됨)
            e.printStackTrace()
        }
        return products
    }

    private fun removeHtmlTags(text: String): String {
        // <b>, </b> 등의 HTML 태그 제거
        return text.replace(Regex("<[^>]*>"), "").replace("&quot;", "\"").replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&")
    }

    private fun mapToProductDto(map: Map<String, String>): ProductDto {
        return ProductDto(
            productId = map["productId"] ?: "",
            title = (map["title"] ?: "").trim(),
            link = (map["link"] ?: "").trim(),
            image = (map["image"] ?: "").trim(),
            lprice = (map["lprice"] ?: "0").trim(),
            hprice = (map["hprice"] ?: "0").trim(),
            mallName = (map["mallName"] ?: "").trim(),
            maker = (map["maker"] ?: "").trim(),
            brand = (map["brand"] ?: "").trim(),
            category1 = (map["category1"] ?: "").trim(),
            category2 = (map["category2"] ?: "").trim(),
            category3 = (map["category3"] ?: "").trim(),
            category4 = (map["category4"] ?: "").trim()
        )
    }
}