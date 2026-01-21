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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.TargetPriceDialog
import com.hkjj.heartbreakprice.presentation.component.WishCard
import com.hkjj.heartbreakprice.ui.AppColors
import com.hkjj.heartbreakprice.R

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
            Text(stringResource(R.string.wish_empty_title), style = MaterialTheme.typography.titleMedium)
            Text(stringResource(R.string.wish_empty_desc), color = AppColors.Gray500)
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
                        text = stringResource(R.string.wish_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Gray900
                    )
                    Text(
                        text = stringResource(R.string.wish_tracking_count, state.wishProducts.size),
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
                    targetPriceText = if (product.price <= product.targetPrice) stringResource(R.string.wish_target_reached) else stringResource(R.string.wish_tracking),
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