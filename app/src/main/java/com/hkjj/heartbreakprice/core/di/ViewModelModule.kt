package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.core.routing.NavigationViewModel
import com.hkjj.heartbreakprice.presentation.screen.notification.NotificationViewModel
import com.hkjj.heartbreakprice.presentation.screen.search.SearchViewModel
import com.hkjj.heartbreakprice.presentation.screen.setting.SettingViewModel
import com.hkjj.heartbreakprice.presentation.screen.signin.SignInViewModel
import com.hkjj.heartbreakprice.presentation.screen.signup.SignUpViewModel
import com.hkjj.heartbreakprice.presentation.screen.wish.WishViewModel
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
            loginUseCase = get(),
            updateFcmTokenUseCase = get(),
            authRepository = get(),
        )
    }

    viewModel {
        SignUpViewModel(
            signUpUseCase = get()
        )
    }

    viewModel {
        SearchViewModel(
            getSearchedProductUseCase = get(),
            addWishUseCase = get(),
            deleteWishUseCase = get(),
            getWishesUseCase = get(),
            saveLastSearchTermUseCase = get(),
            getLastSearchTermUseCase = get()
        )
    }
    
    viewModel {
        WishViewModel(
            addWishUseCase = get(),
            deleteWishUseCase = get(),
            getWishesUseCase = get(),
            updateTargetPriceUseCase = get()
        )
    }

    viewModel {
        NotificationViewModel(
            getNotificationHistoryUseCase = get(),
            readAsMarkNotificationUseCase = get(),
            getUserUseCase = get(),
            updateFcmTokenUseCase = get(),
            deleteFcmTokenUseCase = get(),
            deleteNotificationUseCase = get()
        )
    }
    
    viewModel {
      SettingViewModel(
            logoutUseCase = get(),
            getUserUseCase = get()
       )
    }
}