package com.hkjj.heartbreakprice.presentation.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.hkjj.heartbreakprice.presentation.shopping.model.FavoriteProduct
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun FavoritesScreen(
    favorites: List<FavoriteProduct>,
    onRemove: (String) -> Unit,
    onUpdateTargetPrice: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favorites.isEmpty()) {
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
                text = "총 ${favorites.size}개의 상품을 추적하고 있습니다",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(favorites) { product ->
                    FavoriteProductItem(
                        product = product,
                        onRemove = { onRemove(product.id) },
                        onUpdateTargetPrice = { price -> onUpdateTargetPrice(product.id, price) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteProductItem(
    product: FavoriteProduct,
    onRemove: () -> Unit,
    onUpdateTargetPrice: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TargetPriceDialog(
            product = product,
            onDismiss = { showDialog = false },
            onSave = { price ->
                onUpdateTargetPrice(price)
                showDialog = false
            }
        )
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Image Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Badges
                Column(modifier = Modifier.padding(8.dp)) {
                    product.originalPrice?.let { original ->
                        val discount = ((1 - product.price.toDouble() / original) * 100).toInt()
                         Box(
                            modifier = Modifier
                                .background(Color.Red, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                .padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = "$discount% 할인",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    val priceStatus = getPriceStatus(product.price, product.targetPrice)
                    if (priceStatus != null) {
                        Box(
                            modifier = Modifier
                                .background(priceStatus.second, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = priceStatus.first,
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            // Info Area
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = product.category, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                    Text(
                        text = formatDate(product.addedDate),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(product.shop, style = MaterialTheme.typography.bodySmall, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                // Price
                Text("현재가", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    product.originalPrice?.let {
                        Text(
                            text = "${NumberFormat.getNumberInstance(Locale.KOREA).format(it)}원",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = "${NumberFormat.getNumberInstance(Locale.KOREA).format(product.price)}원",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (product.targetPrice != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("목표가", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = "${NumberFormat.getNumberInstance(Locale.KOREA).format(product.targetPrice)}원",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Actions
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        if (product.targetPrice != null) {
                            Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(16.dp))
                            Text("수정", fontSize = 12.sp)
                        } else {
                            Icon(Icons.Default.NotificationsOff, contentDescription = null, modifier = Modifier.size(16.dp))
                            Text("목표가", fontSize = 12.sp)
                        }
                    }

                    Button(
                        onClick = onRemove,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Red),
                        modifier = Modifier.width(32.dp),
                         contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TargetPriceDialog(
    product: FavoriteProduct,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var priceInput by remember { mutableStateOf(product.targetPrice?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "목표 가격 설정", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text("상품명", style = MaterialTheme.typography.labelMedium)
                Text(product.name, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
                Text("현재가", style = MaterialTheme.typography.labelMedium)
                Text("${NumberFormat.getNumberInstance(Locale.KOREA).format(product.price)}원", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            priceInput = newValue
                        }
                    },
                    label = { Text("목표 가격") },
                    placeholder = { Text("예: 300000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("이 가격 이하로 할인되면 알림을 받습니다", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = onDismiss, colors = ButtonDefaults.textButtonColors()) {
                        Text("취소")
                    }
                    Button(onClick = {
                        val price = priceInput.toIntOrNull()
                        if (price != null && price > 0) {
                            onSave(price)
                        }
                    }) {
                        Text("저장")
                    }
                }
            }
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}

fun getPriceStatus(currentPrice: Int, targetPrice: Int?): Pair<String, Color>? {
    if (targetPrice == null) return null
    if (currentPrice <= targetPrice) {
        return "목표가 도달!" to Color(0xFF22C55E) // Green 500
    }
    val diff = ((currentPrice - targetPrice).toFloat() / targetPrice) * 100
    if (diff <= 10) {
        return "목표가 근접" to Color(0xFFEAB308) // Yellow 500
    }
    return "추적 중" to Color(0xFF3B82F6) // Blue 500
}
