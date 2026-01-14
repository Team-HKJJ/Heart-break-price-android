package com.hkjj.heartbreakprice.domain.model

data class WishProduct (
    val id: String,
    val name: String,
    val price: Int,
    val originalPrice: Int? = null,
    val image: String,
    val category: String,
    val shop: String,
    val targetPrice: Int? = null,
    val addedDate: String = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
)
