package com.hkjj.heartbreakprice.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.presentation.component.EmptyNotificationItem
import com.hkjj.heartbreakprice.presentation.component.NotificationItem
import com.hkjj.heartbreakprice.ui.AppColors
import com.hkjj.heartbreakprice.R

@Composable
fun NotificationScreen(
    uiState: NotificationUiState,
    onAction: (NotificationAction) -> Unit
) {
    val notifications = uiState.notifications
    val unreadCount = notifications.count { !it.isRead }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.notification_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Gray900
                )
                if (unreadCount > 0) {
                    Text(
                        text = pluralStringResource(R.plurals.notification_unread_count, unreadCount, unreadCount),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            if (notifications.isNotEmpty() && unreadCount > 0) {
                TextButton(
                    onClick = { onAction(NotificationAction.MarkAllAsRead) },
                    colors = ButtonDefaults.textButtonColors(contentColor = AppColors.Primary)
                ) {
                    Text(stringResource(R.string.notification_mark_all_read), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Push Notification Setting
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.notification_push_toggle),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = uiState.isPushEnabled,
                onCheckedChange = { onAction(NotificationAction.TogglePushNotification(it)) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppColors.White,
                    checkedTrackColor = AppColors.Primary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (notifications.isEmpty()) {
            EmptyNotificationItem()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notifications, key = { it.id }) { notification ->
                    NotificationItem(
                        notification = notification,
                        onMarkAsRead = { onAction(NotificationAction.MarkAsRead(it)) },
                        onDelete = { onAction(NotificationAction.DeleteNotification(it)) }
                    )
                }
            }
        }
    }
}
