package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.presentation.screen.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SearchViewModel(
            getSearchedProductUseCase = get(),
        )
    }
}
