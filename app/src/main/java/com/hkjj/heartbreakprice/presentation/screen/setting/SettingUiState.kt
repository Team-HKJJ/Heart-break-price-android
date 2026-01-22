package com.hkjj.heartbreakprice.presentation.screen.setting

import com.hkjj.heartbreakprice.domain.model.User

data class SettingUiState(
    val user: User = User("", "", "")
)