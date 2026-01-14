package com.hkjj.heartbreakprice.domain.model

data class Product (
    val id: String,
    val name: String,
    val price: Int,
    val originalPrice: Int? = null,
    val image: String,
    val category: String,
    val shop: String
)