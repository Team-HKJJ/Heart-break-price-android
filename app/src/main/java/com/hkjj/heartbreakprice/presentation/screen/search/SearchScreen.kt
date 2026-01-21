package com.hkjj.heartbreakprice.presentation.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.ProductItem
import com.hkjj.heartbreakprice.ui.AppColors
import com.hkjj.heartbreakprice.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onSearchAction: (SearchAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // 1. Title
        item {
            Text(
                text = stringResource(R.string.nav_search),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Gray900,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
            )
        }

        // 2. Search Bar (Sticky)
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppColors.Background)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextField(
                    value = uiState.searchTerm,
                    onValueChange = { onSearchAction(SearchAction.OnChangeSearchTerm(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            onSearchAction(SearchAction.OnSearch)
                            focusManager.clearFocus()
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = AppColors.Gray500)
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = AppColors.Gray100,
                        unfocusedContainerColor = AppColors.Gray100,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearchAction(SearchAction.OnSearch)
                        focusManager.clearFocus()
                    })
                )
            }
        }

        // 3. Category Filter (Disappears below the sticky search bar)
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.categories) { category ->
                    val isSelected = uiState.selectedCategory == category
                    Surface(
                        onClick = { onSearchAction(SearchAction.CategoryClick(category)) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) AppColors.Primary else AppColors.White,
                        contentColor = if (isSelected) AppColors.White else AppColors.Gray900,
                        border = if (isSelected) null else BorderStroke(1.dp, AppColors.Gray200),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // 4. Product List
        if (uiState.filteredProducts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.search_empty_result), color = AppColors.Gray500)
                }
            }
        } else {
            items(uiState.filteredProducts) { product ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    ProductItem(
                        product = product,
                        isFavorite = uiState.isFavorite(product.id),
                        onToggleFavorite = { onSearchAction(SearchAction.AddToFavorite(product)) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        uiState = SearchUiState(),
        onSearchAction = {},
    )
}