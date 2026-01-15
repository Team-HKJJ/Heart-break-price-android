package com.hkjj.heartbreakprice.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material3.*
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
import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.model.NotificationType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationScreen(
    notifications: List<Notification>,
    onMarkAsRead: (String) -> Unit,
    onMarkAllAsRead: () -> Unit,
    onDelete: (String) -> Unit
) {
    val unreadCount = notifications.count { !it.isRead }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "알림",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                if (unreadCount > 0) {
                    Text(
                        text = "읽지 않은 알림 ${unreadCount}개",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            if (notifications.isNotEmpty() && unreadCount > 0) {
                OutlinedButton(
                    onClick = onMarkAllAsRead,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2563EB)),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF2563EB)))
                ) {
                    Text("모두 읽음 표시")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            EmptyNotificationState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notifications, key = { it.id }) { notification ->
                    NotificationItem(
                        notification = notification,
                        onMarkAsRead = onMarkAsRead,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

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
                            Text("$discount% 할인", color = Color.White)
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

@Composable
fun EmptyNotificationState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFFF3F4F6), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "알림이 없습니다",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "상품을 즐겨찾기하고 목표가를 설정하면\n가격 변동 알림을 받을 수 있습니다.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

fun formatTimeAgo(date: Date): String {
    val now = Date()
    val diff = now.time - date.time
    val minutes = diff / 60000
    val hours = diff / 3600000
    val days = diff / 86400000

    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        days < 7 -> "${days}일 전"
        else -> SimpleDateFormat("MM.dd", Locale.getDefault()).format(date)
    }
}