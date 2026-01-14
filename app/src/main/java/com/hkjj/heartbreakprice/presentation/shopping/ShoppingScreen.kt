package com.hkjj.heartbreakprice.presentation.shopping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel,
    onLogout: () -> Unit
) {
    var currentScreen by rememberSaveable { mutableStateOf("search") }
    val favorites by viewModel.favorites.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val user by viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "가격 추적 쇼핑", 
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = currentScreen == "search",
                    onClick = { currentScreen = "search" },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("검색") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = currentScreen == "favorites",
                    onClick = { currentScreen = "favorites" },
                    icon = {
                        if (favorites.isNotEmpty()) {
                            BadgedBox(
                                badge = {
                                    Badge { Text(favorites.size.toString()) }
                                }
                            ) {
                                Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                            }
                        } else {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                        }
                    },
                    label = { Text("즐겨찾기") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = currentScreen == "notifications",
                    onClick = { currentScreen = "notifications" },
                    icon = {
                        if (notifications.count { !it.isRead } > 0) {
                            BadgedBox(
                                badge = {
                                    Badge { Text(notifications.count { !it.isRead }.toString()) }
                                }
                            ) {
                                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                            }
                        } else {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                    },
                    label = { Text("알림") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = currentScreen == "settings",
                    onClick = { currentScreen = "settings" },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("설정") },
                    alwaysShowLabel = true
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                "search" -> {
                    SearchScreen(
                        onAddToFavorites = viewModel::addToFavorites,
                        isFavorite = viewModel::isFavorite
                    )
                }
                "favorites" -> {
                    FavoritesScreen(
                        favorites = favorites,
                        onRemove = viewModel::removeFromFavorites,
                        onUpdateTargetPrice = viewModel::updateTargetPrice
                    )
                }
                "notifications" -> {
                    NotificationScreen(
                        notifications = notifications,
                        onMarkAsRead = viewModel::markNotificationAsRead,
                        onMarkAllAsRead = viewModel::markAllNotificationsAsRead,
                        onDelete = viewModel::deleteNotification
                    )
                }
                "settings" -> {
                    user?.let {
                        com.hkjj.heartbreakprice.presentation.settings.SettingsScreen(
                            user = it,
                            onLogout = onLogout
                        )
                    }
                }
            }
        }
    }
}

