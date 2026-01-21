package com.hkjj.heartbreakprice.presentation.screen.wish


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.TargetPriceDialog
import com.hkjj.heartbreakprice.presentation.component.WishCard
import com.hkjj.heartbreakprice.ui.AppColors

@Composable
fun WishScreen(
    state: WishUiState,
    onAction: (WishAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isDialogShow) {
        val wishProduct = state.wishProducts.first { it.id == state.targetId }

        TargetPriceDialog(
            wishProduct = wishProduct,
            onDismiss = { onAction(WishAction.OnHideDialog) },
            onSave = { onAction(WishAction.OnTargetPriceChange(wishProduct.id, it)) }
        )
    }

    if (state.wishProducts.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Empty",
                modifier = Modifier.size(64.dp),
                tint = AppColors.Gray300
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("즐겨찾기가 비어있습니다", style = MaterialTheme.typography.titleMedium)
            Text("검색 페이지에서 상품을 즐겨찾기에 추가해보세요", color = AppColors.Gray500)
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.Background),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "즐겨찾기",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Gray900
                    )
                    Text(
                        text = "총 ${state.wishProducts.size}개의 상품을 추적하고 있습니다",
                        color = AppColors.Gray500,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                }
            }

            items(state.wishProducts) { product ->
                WishCard(
                    wishProduct = product,
                    onRemove = {
                        onAction(WishAction.OnDeleteClick(product.id))
                    },
                    targetPriceText = if (product.price <= product.targetPrice) "목표가 도달" else "추적중",
                    targetPriceColor = if (product.price <= product.targetPrice) {
                        AppColors.Success
                    } else {
                        AppColors.Primary
                    },
                    onTargetPriceButtonClick = { onAction(WishAction.OnShowDialog(product.id)) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WishScreenPreview() {
    WishScreen(
        state = WishUiState(),
        onAction = {},
    )
}