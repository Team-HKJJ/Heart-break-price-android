package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.domain.model.Product

class MockProductDataSourceImpl : ProductDataSource {

    override suspend fun getAllProducts(): List<Product> {
        return mockProducts
    }

    val mockProducts = listOf(
        Product(
            id = "1",
            name = "갤럭시 S24 울트라 256GB",
            price = 1299000,
            originalPrice = 1450000,
            image = "https://images.unsplash.com/photo-1676173646307-d050e097d181?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHBob25lJTIwdGVjaG5vbG9neXxlbnwxfHx8fDE3NjgyOTQ5MzV8MA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "스마트폰",
            shop = "G마켓",
            url=""
        ),
        Product(
            id = "2",
            name = "MacBook Pro 14인치 M3 Pro",
            price = 2890000,
            originalPrice = 3190000,
            image = "https://images.unsplash.com/photo-1511385348-a52b4a160dc2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXB0b3AlMjBjb21wdXRlcnxlbnwxfHx8fDE3NjgyOTYzMjN8MA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "노트북",
            shop = "쿠팡",
            url=""
        ),
        Product(
            id = "3",
            name = "Sony WH-1000XM5 노이즈캔슬링",
            price = 329000,
            originalPrice = 459000,
            image = "https://images.unsplash.com/photo-1572119244337-bcb4aae995af?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxoZWFkcGhvbmVzJTIwYXVkaW98ZW58MXx8fHwxNzY4MjM0MzgyfDA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "헤드폰",
            shop = "11번가",
            url=""
        ),
        Product(
            id = "4",
            name = "Apple Watch Series 9 GPS",
            price = 499000,
            originalPrice = 599000,
            image = "https://images.unsplash.com/photo-1719744755507-a4c856c57cf7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHdhdGNoJTIwd2VhcmFibGV8ZW58MXx8fHwxNzY4MjY5MzA1fDA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "스마트워치",
            shop = "네이버쇼핑",
            url=""
        ),
        Product(
            id = "5",
            name = "Canon EOS R6 Mark II 바디",
            price = 3190000,
            originalPrice = null,
            image = "https://images.unsplash.com/photo-1579535984712-92fffbbaa266?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjYW1lcmElMjBwaG90b2dyYXBoeXxlbnwxfHx8fDE3NjgzMjQyMTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "카메라",
            shop = "G마켓",
            url=""
        ),
        Product(
            id = "6",
            name = "iPad Pro 11인치 M2 128GB",
            price = 1049000,
            originalPrice = 1249000,
            image = "https://images.unsplash.com/photo-1714071803623-9594e3b77862?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0YWJsZXQlMjBkZXZpY2V8ZW58MXx8fHwxNzY4MzM1MzA1fDA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "태블릿",
            shop = "쿠팡",
            url=""
        ),
        Product(
            id = "7",
            name = "iPhone 15 Pro Max 256GB",
            price = 1590000,
            originalPrice = 1750000,
            image = "https://images.unsplash.com/photo-1676173646307-d050e097d181?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzbWFydHBob25lJTIwdGVjaG5vbG9neXxlbnwxfHx8fDE3NjgyOTQ5MzV8MA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "스마트폰",
            shop = "네이버쇼핑",
            url=""
        ),
        Product(
            id = "8",
            name = "Dell XPS 15 9530 i7",
            price = 2390000,
            originalPrice = null,
            image = "https://images.unsplash.com/photo-1511385348-a52b4a160dc2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXB0b3AlMjBjb21wdXRlcnxlbnwxfHx8fDE3NjgyOTYzMjN8MA&ixlib=rb-4.1.0&q=80&w=1080",
            category = "노트북",
            shop = "11번가",
            url=""
        ),
    )
}