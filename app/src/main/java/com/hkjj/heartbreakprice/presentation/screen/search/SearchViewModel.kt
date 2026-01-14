package com.hkjj.heartbreakprice.presentation.screen.search

import androidx.lifecycle.ViewModel
import com.hkjj.heartbreakprice.data.data_source.MockProductDataSourceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState(filteredProducts = MockProductDataSourceImpl().mockProducts))
    val uiState = _uiState.asStateFlow()

    fun onAction(action: SearchAction) {

    }
}