package com.hkjj.heartbreakprice.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person

import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hkjj.heartbreakprice.presentation.shopping.model.User

@Composable
fun SettingsScreen(
    user: User,
    onLogout: () -> Unit
) {
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
            title = "계정 설정",
            items = listOf(
                SettingsItem("프로필 수정", Icons.Default.Person),
                SettingsItem("비밀번호 변경", Icons.Default.Security)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Notification Settings
        SettingsSection(
            title = "알림 설정",
            items = listOf(
                SettingsItem("가격 알림", Icons.Default.Notifications, "켜짐"),
                SettingsItem("마케팅 알림", Icons.Default.Notifications, "꺼짐")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // App Info
        SettingsSection(
            title = "앱 정보",
            items = listOf(
                SettingsItem("고객 지원", Icons.Default.Info),
                SettingsItem("이용약관", Icons.Default.Info),
                SettingsItem("버전 1.0.0", Icons.Default.Info, showArrow = false)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("로그아웃")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val value: String? = null,
    val showArrow: Boolean = true
)

@Composable
fun SettingsSection(title: String, items: List<SettingsItem>) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Divider(color = Color(0xFFE5E7EB), thickness = 1.dp)
            
            items.forEachIndexed { index, item ->
                 Row(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clickable(enabled = item.showArrow) {}
                         .padding(horizontal = 24.dp, vertical = 16.dp),
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Icon(item.icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                     Spacer(modifier = Modifier.width(12.dp))
                     Text(item.title, modifier = Modifier.weight(1f), color = Color(0xFF374151))
                     
                     if (item.value != null) {
                        Text(item.value, color = Color.Gray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                     }
                     
                     if (item.showArrow) {
                         Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color(0xFF9CA3AF))
                     }
                 }
                 if (index < items.size - 1) {
                     Divider(color = Color(0xFFF3F4F6), thickness = 1.dp, modifier = Modifier.padding(horizontal = 24.dp))
                 }
            }
        }
    }
}
