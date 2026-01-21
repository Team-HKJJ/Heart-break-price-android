package com.hkjj.heartbreakprice.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hkjj.heartbreakprice.core.routing.NavigationAction
import com.hkjj.heartbreakprice.presentation.screen.notification.NotificationRoot
import com.hkjj.heartbreakprice.presentation.screen.search.SearchRoot
import com.hkjj.heartbreakprice.presentation.screen.setting.SettingRoot
import com.hkjj.heartbreakprice.presentation.screen.wish.WishRoot
import com.hkjj.heartbreakprice.ui.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRoot(
    bottomNavController: NavHostController = rememberNavController(),
    onNavigationAction: (NavigationAction) -> Unit
) {
    val items = listOf(
        Triple("search", Icons.Default.Search, "검색"),
        Triple("wish", Icons.Default.Favorite, "즐겨찾기"),
        Triple("notification", Icons.Default.Notifications, "알림"),
        Triple("settings", Icons.Default.Settings, "설정")
    )
    val currentRoute by bottomNavController.currentBackStackEntryAsState()
    val isSelected = { route: String -> currentRoute?.destination?.route == route }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "심쿵가",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppColors.White,
                    titleContentColor = AppColors.Gray900
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AppColors.White
            ) {
                items.forEach { (route, icon, label) ->
                    val selected = isSelected(route)
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            bottomNavController.navigate(route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { 
                            Icon(
                                icon, 
                                contentDescription = label,
                                tint = if (selected) AppColors.Primary else AppColors.Gray400
                            ) 
                        },
                        label = { 
                            Text(
                                label,
                                color = if (selected) AppColors.Primary else AppColors.Gray400,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = AppColors.Transparent,
                            selectedIconColor = AppColors.Primary,
                            unselectedIconColor = AppColors.Gray400,
                            selectedTextColor = AppColors.Primary,
                            unselectedTextColor = AppColors.Gray400
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "search",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("search") { SearchRoot() }
            composable("wish") { WishRoot() }
            composable("notification") { NotificationRoot() }
            composable("settings") {
                SettingRoot(
                    onLogout = {
                        onNavigationAction(NavigationAction.NavigateToSignIn)
                    }
                )
            }
        }
    }
}
