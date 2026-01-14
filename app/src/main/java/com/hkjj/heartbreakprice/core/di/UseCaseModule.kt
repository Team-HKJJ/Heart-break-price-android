package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetSearchedProductUseCase
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetSearchedProductUseCase(get()) }
    factory { AddWishUseCase(get()) }
    factory { DeleteWishUseCase(get()) }
}