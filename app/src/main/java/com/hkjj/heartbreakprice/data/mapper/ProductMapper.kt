package com.hkjj.heartbreakprice.data.mapper

import com.hkjj.heartbreakprice.data.dto.ProductDto
import com.hkjj.heartbreakprice.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = productId ?: "",
        name = title ?: "",
        price = lprice?.filter { it.isDigit() }?.toIntOrNull() ?: 0,
        originalPrice = hprice?.filter { it.isDigit() }?.toIntOrNull(),
        image = image ?: "",
        category = category3 ?: "",
        brand = brand ?: "",
        shop = mallName ?: "",
        url = link ?: ""
    )
}