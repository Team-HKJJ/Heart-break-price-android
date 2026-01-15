package com.hkjj.heartbreakprice.presentation.screen.settings

import com.hkjj.heartbreakprice.presentation.shopping.model.User

data class SettingUiState(
    val user: User = User("", "")
)
