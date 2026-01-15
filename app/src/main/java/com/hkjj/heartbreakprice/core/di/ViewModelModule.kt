package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.presentation.screen.wish.WishViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WishViewModel(
            addWishUseCase = get(),
            deleteWishUseCase = get(),
            getWishesUseCase = get()
        )
    }
}