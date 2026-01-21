package com.hkjj.heartbreakprice.core.routing

sealed interface NavigationAction {
    data object NavigateToSignUp : NavigationAction
    data object NavigateToSignIn : NavigationAction
    data class NavigateToMain(val initialTab: String? = null) : NavigationAction
    data object NavigateBack : NavigationAction
    data class NavigateToDetail(val id: String) : NavigationAction
}