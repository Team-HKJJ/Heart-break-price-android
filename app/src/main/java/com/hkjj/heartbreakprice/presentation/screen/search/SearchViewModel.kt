package com.hkjj.heartbreakprice.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase

import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val getSearchedProductUseCase: GetSearchedProductUseCase,
    private val addWishUseCase: AddWishUseCase,
    private val deleteWishUseCase: DeleteWishUseCase,
    private val getWishesUseCase: GetWishesUseCase
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
        
        // 찜 목록 실시간 관찰
        viewModelScope.launch {
            getWishesUseCase().collect { wishes ->
                _uiState.update { 
                    it.copy(favoriteProductIds = wishes.map { wish -> wish.id }.toSet())
                }
            }
        }

    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.AddToFavorite -> {
                viewModelScope.launch {
                    val currentFavorites = _uiState.value.favoriteProductIds
                    if (currentFavorites.contains(action.product.id)) {
                        val result = deleteWishUseCase(action.product.id)
                        if (result is Result.Success) {
                            _uiState.value = _uiState.value.copy(
                                favoriteProductIds = currentFavorites - action.product.id
                            )
                        }
                    } else {
                        val wishProduct = WishProduct(
                            id = action.product.id,
                            name = action.product.name,
                            price = action.product.price,
                            originalPrice = action.product.originalPrice,
                            image = action.product.image,
                            category = action.product.category,
                            shop = action.product.shop,
                            targetPrice = action.product.price, // 초기 목표가는 현재가로 설정
                            url = action.product.url
                        )
                        val result = addWishUseCase(wishProduct)
                        if (result is Result.Success) {
                            _uiState.value = _uiState.value.copy(
                                favoriteProductIds = currentFavorites + action.product.id
                            )
                        }
                    }
                }
            }
            is SearchAction.CategoryClick -> {
                _uiState.value = _uiState.value.copy(selectedCategory = action.category)
                updateFilteredProducts()
            }
            is SearchAction.OnChangeSearchTerm -> {
                _uiState.value = _uiState.value.copy(searchTerm = action.searchTerm)
            }
            is SearchAction.OnSearch -> {
                viewModelScope.launch {
                    val result = getSearchedProductUseCase(_uiState.value.searchTerm)
                    if (result is Result.Success) {
                        allProducts = result.data
                        updateCategories()
                        updateFilteredProducts()
                    }
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
