package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetUserUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase
import com.hkjj.heartbreakprice.domain.usecase.LogoutUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetSearchedProductUseCase(get()) }
    factory { AddWishUseCase(get()) }
    factory { DeleteWishUseCase(get()) }
    factory { GetWishesUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetUserUseCase(get()) }
}