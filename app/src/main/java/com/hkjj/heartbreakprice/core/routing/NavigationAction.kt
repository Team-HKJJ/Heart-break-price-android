package com.hkjj.heartbreakprice.core.routing

sealed interface NavigationAction {
    data object NavigateToSignUp : NavigationAction
    data object NavigateToMain : NavigationAction
    data object NavigateBack : NavigationAction
    data class NavigateToDetail(val id: String) : NavigationAction
}