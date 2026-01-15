package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetNotificationHistoryUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase
import com.hkjj.heartbreakprice.domain.usecase.ReadAsMarkNotificationUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetSearchedProductUseCase(get()) }
    factory { AddWishUseCase(get()) }
    factory { DeleteWishUseCase(get()) }
    factory { GetWishesUseCase(get()) }
    factory { GetNotificationHistoryUseCase(get()) }
    factory { ReadAsMarkNotificationUseCase(get()) }
}