package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.data.repository.AuthRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.MockWishRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.NotificationHistoryRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.ProductRepositoryImpl
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import com.hkjj.heartbreakprice.domain.repository.ProductRepository
import com.hkjj.heartbreakprice.domain.repository.WishRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }
    single<WishRepository> {
        MockWishRepositoryImpl()
    }
    single {
        AuthRepositoryImpl()
    }
    single<NotificationHistoryRepository> {
        NotificationHistoryRepositoryImpl(get())
    }
}