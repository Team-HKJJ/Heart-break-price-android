package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.core.routing.NavigationViewModel
import com.hkjj.heartbreakprice.presentation.screen.search.SearchViewModel
import com.hkjj.heartbreakprice.presentation.screen.signin.SignInViewModel
import com.hkjj.heartbreakprice.presentation.screen.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NavigationViewModel(
            authRepository = get()
        )
    }

    viewModel {
        SignInViewModel(
            authRepository = get()
        )
    }

    viewModel {
        SignUpViewModel(
            authRepository = get()
        )
    }

    viewModel {
        SearchViewModel(
            getSearchedProductUseCase = get(),
        )
    }
}