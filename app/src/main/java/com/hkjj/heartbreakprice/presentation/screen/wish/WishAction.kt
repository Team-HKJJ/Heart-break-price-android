package com.hkjj.heartbreakprice.presentation.screen.wish

sealed interface WishAction {
    data object OnHideDialog : WishAction
    data class OnShowDialog(val id: String) : WishAction

    data class OnDeleteClick(val id: String) : WishAction
    data class OnTargetPriceChange(val id: String, val targetPrice: Int) : WishAction
}