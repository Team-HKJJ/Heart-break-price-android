package com.hkjj.heartbreakprice.presentation.screen.search

import com.hkjj.heartbreakprice.domain.model.Product

sealed interface SearchAction {
    data class AddToFavorite(val product: Product) : SearchAction
    data class OnChangeSearchTerm(val searchTerm: String) : SearchAction
    data class CategoryClick(val category: String) : SearchAction
    data object OnSearch : SearchAction
}