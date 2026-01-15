package com.hkjj.heartbreakprice.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hkjj.heartbreakprice.core.util.TimeUtil.formatTimeAgo
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.model.NotificationType

@Composable
fun NotificationItem(
    notification: Notification,
    onMarkAsRead: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!notification.isRead) {
                    Modifier.border(2.dp, Color(0xFFDBEAFE), RoundedCornerShape(12.dp))
                } else {
                    Modifier
                }
            )
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            // Product Image
            AsyncImage(
                model = notification.productImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.Top) {
                    // Icon Box
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = when (notification.type) {
                                    NotificationType.PRICE_DROP -> Color(0xFFEFF6FF)
                                    NotificationType.TARGET_REACHED -> Color(0xFFF0FDF4)
                                    NotificationType.BACK_IN_STOCK -> Color(0xFFFFF7ED)
                                },
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (notification.type) {
                                NotificationType.PRICE_DROP -> Icons.Default.TrendingDown
                                NotificationType.TARGET_REACHED -> Icons.Default.Check
                                NotificationType.BACK_IN_STOCK -> Icons.Default.Notifications
                            },
                            contentDescription = null,
                            tint = when (notification.type) {
                                NotificationType.PRICE_DROP -> Color(0xFF2563EB)
                                NotificationType.TARGET_REACHED -> Color(0xFF16A34A)
                                NotificationType.BACK_IN_STOCK -> Color(0xFFEA580C)
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = notification.productName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = notification.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4B5563)
                        )
                    }
                }

                if (notification.oldPrice != null && notification.newPrice != null) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${notification.oldPrice}원",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9CA3AF),
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${notification.newPrice}원",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2563EB)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Badge(containerColor = Color(0xFFEF4444)) {
                            val discount = ((1.0 - notification.newPrice.toDouble() / notification.oldPrice) * 100).toInt()
                            Text("$discount% 할인", color = Color.White, maxLines = 1)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(12.dp)) // Using Notifications as Clock replacement
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = formatTimeAgo(notification.timestamp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF6B7280)
                        )
                    }

                    Row {
                        if (!notification.isRead) {
                            TextButton(
                                onClick = { onMarkAsRead(notification.id) },
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("읽음 표시", color = Color(0xFF2563EB), fontSize = 12.sp)
                            }
                        }
                        IconButton(
                            onClick = { onDelete(notification.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "삭제", tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}