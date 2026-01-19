package com.hkjj.heartbreakprice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NaverShoppingResponseDto(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<ProductDto>
)
