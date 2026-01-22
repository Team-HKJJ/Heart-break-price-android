package com.hkjj.heartbreakprice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val productId: String? = null,
    val title: String? = null,
    val link: String? = null,
    val image: String? = null,
    val lprice: String? = null,
    val hprice: String? = null,
    val mallName: String? = null,
    val maker: String? = null,
    val brand: String? = null,
    val category1: String? = null,
    val category2: String? = null,
    val category3: String? = null,
    val category4: String? = null
)
