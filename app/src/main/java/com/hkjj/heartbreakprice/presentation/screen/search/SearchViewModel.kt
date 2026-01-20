package com.hkjj.heartbreakprice.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.Product

class SearchViewModel(
    private val getSearchedProductUseCase: GetSearchedProductUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    init {
        // 초기 로딩 시 전체 상품을 가져옵니다.
        viewModelScope.launch {
            val result = getSearchedProductUseCase("")
            if (result is Result.Success) {
                allProducts = result.data
                updateCategories()
                updateFilteredProducts()
            }
        }
        observeSearchTerm()
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.AddToFavorite -> {
                val currentFavorites = _uiState.value.favoriteProductIds
                val newFavorites = if (currentFavorites.contains(action.product.id)) {
                    currentFavorites - action.product.id
                } else {
                    currentFavorites + action.product.id
                }
                _uiState.value = _uiState.value.copy(favoriteProductIds = newFavorites)
            }
            is SearchAction.CategoryClick -> {
                _uiState.value = _uiState.value.copy(selectedCategory = action.category)
                updateFilteredProducts()
            }
            is SearchAction.OnChangeSearchTerm -> {
                _uiState.value = _uiState.value.copy(searchTerm = action.searchTerm)
            }
        }
    }

    private fun observeSearchTerm() {
        viewModelScope.launch {
            _uiState
                .map { it.searchTerm }
                .debounce(300)
                .collect { query ->
                    val result = getSearchedProductUseCase(query)
                    if (result is Result.Success) {
                        allProducts = result.data
                        updateCategories()
                        updateFilteredProducts()
                    }
                }
        }
    }

    private fun updateCategories() {
        val uniqueCategories = allProducts.map { it.brand }.distinct().filter { it.isNotBlank() }
        val hasEmptyBrand = allProducts.any { it.brand.isBlank() }
        val finalCategories = if (hasEmptyBrand) {
            listOf("전체") + uniqueCategories + "기타"
        } else {
            listOf("전체") + uniqueCategories
        }
        _uiState.value = _uiState.value.copy(categories = finalCategories)
    }

    private fun updateFilteredProducts() {
        val selectedCategory = _uiState.value.selectedCategory
        val filtered = when (selectedCategory) {
            "전체" -> allProducts
            "기타" -> allProducts.filter { it.brand.isBlank() }
            else -> allProducts.filter { it.brand == selectedCategory }
        }
        _uiState.value = _uiState.value.copy(filteredProducts = filtered)
    }
}
