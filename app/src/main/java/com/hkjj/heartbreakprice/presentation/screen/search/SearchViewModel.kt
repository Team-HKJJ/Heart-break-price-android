package com.hkjj.heartbreakprice.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.data.data_source.MockProductDataSourceImpl
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
                        updateFilteredProducts()
                    }
                }
        }
    }

    private fun updateFilteredProducts() {
        val selectedCategory = _uiState.value.selectedCategory
        val filtered = if (selectedCategory == "전체") {
            allProducts
        } else {
            allProducts.filter { it.category == selectedCategory }
        }
        _uiState.value = _uiState.value.copy(filteredProducts = filtered)
    }
}