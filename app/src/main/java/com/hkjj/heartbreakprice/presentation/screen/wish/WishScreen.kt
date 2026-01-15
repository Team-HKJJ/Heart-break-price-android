package com.hkjj.heartbreakprice.presentation.screen.wish


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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.WishCard

@Composable
fun WishScreen(
    state: WishUiState,
    onRemove: (String) -> Unit,
    onUpdateTargetPrice: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
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
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("즐겨찾기가 비어있습니다", style = MaterialTheme.typography.titleMedium)
            Text("검색 페이지에서 상품을 즐겨찾기에 추가해보세요", color = Color.Gray)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(16.dp)
        ) {
            Text(
                text = "즐겨찾기",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "총 ${state.wishProducts.size}개의 상품을 추적하고 있습니다",
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
                        onRemove = { onRemove(product.id) },
                        targetPriceText =if (product.price <= product.targetPrice) "목표가 도달" else "추적중",
                        targetPriceColor = if (product.price <= product.targetPrice) Color(
                            0xFF22C55E
                        ) else Color(0xFF3B82F6),
                        onTargetPriceButtonClick = {}
                        //price -> onUpdateTargetPrice(product.id, price)
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
        onRemove = {},
        onUpdateTargetPrice = {_,_ ->},
    )

}