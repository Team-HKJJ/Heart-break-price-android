package com.hkjj.heartbreakprice.presentation.screen.search

import com.hkjj.heartbreakprice.domain.model.Product

data class SearchUiState(
    val searchTerm: String = "",
    val selectedCategory: String = "전체",
    val filteredProducts: List<Product> = emptyList(),
    val favoriteProductIds: Set<String> = emptySet()
) {
    fun isFavorite(id: String): Boolean = favoriteProductIds.contains(id)
}