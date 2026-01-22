package com.hkjj.heartbreakprice.presentation.screen.setting

sealed interface SettingAction {
    data object OnLogoutClick : SettingAction
}
