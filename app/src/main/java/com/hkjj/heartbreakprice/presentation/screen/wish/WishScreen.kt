package com.hkjj.heartbreakprice.presentation.screen.wish

import com.hkjj.heartbreakprice.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.TargetPriceDialog
import com.hkjj.heartbreakprice.presentation.component.WishCard

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
                contentDescription = stringResource(R.string.wish_empty_icon_desc),
                modifier = Modifier.size(64.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.wish_empty_title), style = MaterialTheme.typography.titleMedium)
            Text(stringResource(R.string.wish_empty_desc), color = Color.Gray)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.wish_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.wish_tracking_count,
                    state.wishProducts.size
                ),
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(state.wishProducts) { product ->
                    WishCard(
                        wishProduct = product,
                        onRemove = {
                            onAction(WishAction.OnDeleteClick(product.id))
                        },
                        targetPriceResId = if (product.price <= product.targetPrice) R.string.wish_target_reached else R.string.wish_tracking,
                        targetPriceColor = if (product.price <= product.targetPrice) {
                            Color(0xFF22C55E)
                        } else {
                            Color(0xFF3B82F6)
                        },
                        onTargetPriceButtonClick = { onAction(WishAction.OnShowDialog(product.id)) }
                    )
                }
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