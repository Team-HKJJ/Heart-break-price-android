package com.hkjj.heartbreakprice.presentation.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.domain.model.SettingItem
import com.hkjj.heartbreakprice.presentation.component.SettingsSection
import com.hkjj.heartbreakprice.R

@Composable
fun SettingScreen(
    uiState: SettingUiState,
    onAction: (SettingAction) -> Unit
) {
    val user = uiState.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // User Profile
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2563EB)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(user.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(user.email, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Account Settings
        SettingsSection(
            title = stringResource(R.string.settings_account),
            items = listOf(
                SettingItem(stringResource(R.string.settings_profile_edit), Icons.Default.Person),
                SettingItem(stringResource(R.string.settings_password_change), Icons.Default.Security)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Notification Settings
        SettingsSection(
            title = stringResource(R.string.settings_notification),
            items = listOf(
                SettingItem(stringResource(R.string.settings_price_notification), Icons.Default.Notifications, "켜짐"),
                SettingItem(stringResource(R.string.settings_marketing_notification), Icons.Default.Notifications, "꺼짐")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // App Info
        SettingsSection(
            title = stringResource(R.string.settings_app_info),
            items = listOf(
                SettingItem(stringResource(R.string.settings_support), Icons.Default.Info),
                SettingItem(stringResource(R.string.settings_terms), Icons.Default.Info),
                SettingItem(stringResource(
                    R.string.settings_version,
                    "1.0.0"
                ), Icons.Default.Info, showArrow = false)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAction(SettingAction.OnLogoutClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.settings_logout))
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}