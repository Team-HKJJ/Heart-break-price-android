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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.domain.model.SettingItem
import com.hkjj.heartbreakprice.presentation.component.SettingsSection
import com.hkjj.heartbreakprice.ui.AppColors

@Composable
fun SettingScreen(
    uiState: SettingUiState,
    onAction: (SettingAction) -> Unit
) {
    val user = uiState.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // User Profile
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.White),
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
                        .background(AppColors.Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = AppColors.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(user.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = AppColors.Gray500, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(user.email, color = AppColors.Gray500, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Account Settings
        SettingsSection(
            title = "계정 설정",
            items = listOf(
                SettingItem("프로필 수정", Icons.Default.Person),
                SettingItem("비밀번호 변경", Icons.Default.Security)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Notification Settings
        SettingsSection(
            title = "알림 설정",
            items = listOf(
                SettingItem("가격 알림", Icons.Default.Notifications, "켜짐"),
                SettingItem("마케팅 알림", Icons.Default.Notifications, "꺼짐")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // App Info
        SettingsSection(
            title = "앱 정보",
            items = listOf(
                SettingItem("고객 지원", Icons.Default.Info),
                SettingItem("이용약관", Icons.Default.Info),
                SettingItem("버전 1.0.0", Icons.Default.Info, showArrow = false)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onAction(SettingAction.OnLogoutClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Error),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("로그아웃")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}