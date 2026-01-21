package com.hkjj.heartbreakprice.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hkjj.heartbreakprice.domain.model.WishProduct
import java.text.NumberFormat
import java.util.Locale
import com.hkjj.heartbreakprice.R

@Composable
fun WishCard(
    wishProduct: WishProduct,
    targetPriceResId: Int,
    targetPriceColor: Color,
    onTargetPriceButtonClick: () -> Unit,
    onRemove: () -> Unit,
) {
    val targetPriceText = stringResource(targetPriceResId)

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
                    model = wishProduct.image,
                    contentDescription = wishProduct.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Badges
                Row(modifier = Modifier.padding(8.dp)) {
                    wishProduct.originalPrice?.let { original ->
                        val discount = ((1 - wishProduct.price.toDouble() / original) * 100).toInt()
                        Box(
                            modifier = Modifier
                                .background(Color.Red, RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp)
                                .padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.wish_discount,
                                    discount
                                ),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Spacer(Modifier.width(4.dp))

                    if (targetPriceText.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .background(targetPriceColor, RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp)
                                .padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = targetPriceText,
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
                        Text(text = wishProduct.category, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color.Gray
                    )
                    Text(
                        text = wishProduct.addedDate.substring(0, 10),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = wishProduct.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(wishProduct.shop, style = MaterialTheme.typography.bodySmall, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                // Price
                Text(stringResource(R.string.wish_current_price), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    wishProduct.originalPrice?.let {
                        Text(
                            text = stringResource(
                                R.string.price_format,
                                NumberFormat.getNumberInstance(Locale.KOREA).format(it)
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = stringResource(
                            R.string.price_format,
                            NumberFormat.getNumberInstance(Locale.KOREA).format(wishProduct.price)
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (wishProduct.targetPrice != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(stringResource(R.string.wish_target_price), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(
                        text = stringResource(
                            R.string.price_format,
                            NumberFormat.getNumberInstance(Locale.KOREA).format(wishProduct.targetPrice)
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Actions
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onTargetPriceButtonClick,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.outlinedButtonColors(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        if (wishProduct.targetPrice != null) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(stringResource(R.string.wish_action_edit), fontSize = 12.sp)
                        } else {
                            Icon(
                                Icons.Default.NotificationsOff,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(stringResource(R.string.wish_action_set_target), fontSize = 12.sp)
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



@Preview(showBackground = true)
@Composable
private fun WishCardPreview() {
    val wishProduct = WishProduct(
        id = "1",
        name = "Canon EOS R6 Mark II 바디",
        price = 3190000,
        originalPrice = 3500000,
        image = "https://images.unsplash.com/photo-1579535984712-92fffbbaa266?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjYW1lcmElMjBwaG90b2dyYXBoeXxlbnwxfHx8fDE3NjgzMjQyMTB8MA&ixlib=rb-4.1.0&q=80&w=1080",
        category = "카메라",
        shop = "G마켓",
        targetPrice = 1000000,
        addedDate = "2026-01-14T14:45:20",
        url=""
    )

    WishCard(
        wishProduct = wishProduct,
        targetPriceResId = R.string.wish_tracking,
        targetPriceColor = Color(0xFF3B82F6),
        onTargetPriceButtonClick = {},
        onRemove = {}
    )
}