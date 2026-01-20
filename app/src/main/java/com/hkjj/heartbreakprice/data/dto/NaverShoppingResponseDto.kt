package com.hkjj.heartbreakprice.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NaverShoppingResponseDto(
    val lastBuildDate: String? = null,
    val total: Int? = null,
    val start: Int? = null,
    val display: Int? = null,
    val items: List<ProductDto>? = null
)
