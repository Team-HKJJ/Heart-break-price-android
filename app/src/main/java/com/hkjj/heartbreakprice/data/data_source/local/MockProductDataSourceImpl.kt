package com.hkjj.heartbreakprice.data.data_source.local

import com.hkjj.heartbreakprice.core.network.ApiResponse
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.dto.ProductDto

class MockProductDataSourceImpl : ProductDataSource {

    override suspend fun getProducts(query: String): ApiResponse<List<ProductDto>> {
        return ApiResponse.Success(
            statusCode = 200,
            headers = emptyMap(),
            body = mockProductDtos.filter { it.title?.contains(query, ignoreCase = true) ?: false }
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
        ),
        ProductDto(
            productId = "3",
            title = "Sony WH-1000XM5 노이즈캔슬링",
            link = "",
            image = "https://images.unsplash.com/photo-1572119244337-bcb4aae995af?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxoZWFkcGhvbmVzJTIwYXVkaW98ZW58MXx8fHwxNzY4MjM0MzgyfDA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "329000",
            hprice = "459000",
            mallName = "11번가",
            maker = "Sony",
            brand = "Sony",
            category1 = "디지털/가전",
            category2 = "음향기기",
            category3 = "헤드폰",
            category4 = ""
        ),
        ProductDto(
            productId = "4",
            title = "Apple Watch Series 9 GPS",
            link = "",
            image = "https://images.unsplash.com/photo-1719744755507-a4c856c57cf7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHdhdGNoJTIwd2VhcmFibGV8ZW58MXx8fHwxNzY4MjY5MzA1fDA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "499000",
            hprice = "599000",
            mallName = "네이버쇼핑",
            maker = "Apple",
            brand = "Apple",
            category1 = "디지털/가전",
            category2 = "스마트기기",
            category3 = "스마트워치",
            category4 = ""
        ),
        ProductDto(
            productId = "5",
            title = "Canon EOS R6 Mark II 바디",
            link = "",
            image = "https://images.unsplash.com/photo-1579535984712-92fffbbaa266?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjYW1lcmElMjBwaG90b2dyYXBoeXxlbnwxfHx8fDE3NjgzMjQyMTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "3190000",
            hprice = "",
            mallName = "G마켓",
            maker = "Canon",
            brand = "Canon",
            category1 = "디지털/가전",
            category2 = "카메라",
            category3 = "카메라",
            category4 = ""
        ),
        ProductDto(
            productId = "6",
            title = "iPad Pro 11인치 M2 128GB",
            link = "",
            image = "https://images.unsplash.com/photo-1714071803623-9594e3b77862?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0YWJsZXQlMjBkZXZpY2V8ZW58MXx8fHwxNzY4MzM1MzA1fDA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "1049000",
            hprice = "1249000",
            mallName = "쿠팡",
            maker = "Apple",
            brand = "Apple",
            category1 = "디지털/가전",
            category2 = "태블릿PC",
            category3 = "태블릿",
            category4 = ""
        ),
        ProductDto(
            productId = "7",
            title = "iPhone 15 Pro Max 256GB",
            link = "",
            image = "https://images.unsplash.com/photo-1676173646307-d050e097d181?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHBob25lJTIwdGVjaG5vbG9neXxlbnwxfHx8fDE3NjgyOTQ5MzV8MA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "1590000",
            hprice = "1750000",
            mallName = "네이버쇼핑",
            maker = "Apple",
            brand = "Apple",
            category1 = "디지털/가전",
            category2 = "스마트폰",
            category3 = "스마트폰",
            category4 = ""
        ),
        ProductDto(
            productId = "8",
            title = "Dell XPS 15 9530 i7",
            link = "",
            image = "https://images.unsplash.com/photo-1511385348-a52b4a160dc2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXB0b3AlMjBjb21wdXRlcnxlbnwxfHx8fDE3NjgyOTYzMjN8MA&ixlib=rb-4.1.0&q=80&w=1080",
            lprice = "2390000",
            hprice = "",
            mallName = "11번가",
            maker = "Dell",
            brand = "Dell",
            category1 = "디지털/가전",
            category2 = "노트북",
            category3 = "노트북",
            category4 = ""
        )
    )
}
