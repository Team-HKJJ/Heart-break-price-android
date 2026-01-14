package com.hkjj.heartbreakprice.presentation.screen.search

import com.hkjj.heartbreakprice.domain.model.Product

data class SearchUiState(
    val searchTerm: String = "",
    val selectedCategory: String = "전체",
    val filteredProducts: List<Product> = emptyList()
)