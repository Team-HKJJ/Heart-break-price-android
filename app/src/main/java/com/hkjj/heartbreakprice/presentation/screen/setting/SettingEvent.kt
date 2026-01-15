package com.hkjj.heartbreakprice.presentation.screen.setting

sealed interface SettingEvent {
    data object Logout : SettingEvent
}
