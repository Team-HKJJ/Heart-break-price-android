package com.hkjj.heartbreakprice.presentation.screen.wish

import com.hkjj.heartbreakprice.domain.model.WishProduct

data class WishUiState(
    val wishProducts: List<WishProduct> = emptyList(),
    val errormsg: String? = null
)

