package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteFcmTokenUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetNotificationHistoryUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetSignInStatusUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetUserUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase
import com.hkjj.heartbreakprice.domain.usecase.LoginUseCase
import com.hkjj.heartbreakprice.domain.usecase.ReadAsMarkNotificationUseCase
import com.hkjj.heartbreakprice.domain.usecase.LogoutUseCase
import com.hkjj.heartbreakprice.domain.usecase.SignUpUseCase
import com.hkjj.heartbreakprice.domain.usecase.UpdateFcmTokenUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetSearchedProductUseCase(get()) }
    factory { AddWishUseCase(get()) }
    factory { DeleteWishUseCase(get()) }
    factory { GetWishesUseCase(get()) }
    factory { GetNotificationHistoryUseCase(get()) }
    factory { ReadAsMarkNotificationUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { UpdateFcmTokenUseCase(get(), get()) }
    factory { GetSignInStatusUseCase(get()) }
    factory { DeleteFcmTokenUseCase(get()) }
}