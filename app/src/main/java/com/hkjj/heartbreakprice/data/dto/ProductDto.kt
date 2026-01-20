package com.hkjj.heartbreakprice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val productId: String,
    val title: String,
    val link: String,
    val image: String,
    val lprice: String,
    val hprice: String,
    val mallName: String,
    val maker: String,
    val brand: String,
    val category1: String,
    val category2: String,
    val category3: String,
    val category4: String
)
