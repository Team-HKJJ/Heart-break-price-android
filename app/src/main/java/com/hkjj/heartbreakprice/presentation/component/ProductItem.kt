package com.hkjj.heartbreakprice.presentation.component

import com.hkjj.heartbreakprice.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hkjj.heartbreakprice.domain.model.Product
import java.text.NumberFormat
import java.util.*

@Composable
fun ProductItem(
    product: Product,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Product Image
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                modifier = Modifier
                    .size(96.dp) // sm:size-32 usually 128px, using 96dp approx
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category Badge
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = product.category,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    // Discount Badge
                    product.originalPrice?.let { original ->
                        val discount = ((1 - product.price.toDouble() / original) * 100).toInt()
                        Box(
                            modifier = Modifier
                                .background(Color.Red, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.product_discount,
                                    discount
                                ),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
                Text(
                    text = product.shop,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        product.originalPrice?.let {
                            Text(
                                text = "${NumberFormat.getNumberInstance(Locale.KOREA).format(it)}원",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                        Text(
                            text = "${NumberFormat.getNumberInstance(Locale.KOREA).format(product.price)}원",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = onToggleFavorite,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(32.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = if (isFavorite) {
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        } else {
                            ButtonDefaults.outlinedButtonColors()
                        },
                        border = if (!isFavorite) androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray) else null
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.White else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}