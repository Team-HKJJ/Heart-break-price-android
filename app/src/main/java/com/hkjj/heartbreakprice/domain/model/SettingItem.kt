package com.hkjj.heartbreakprice.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItem(
    val title: String,
    val icon: ImageVector,
    val value: String? = null,
    val showArrow: Boolean = true
)