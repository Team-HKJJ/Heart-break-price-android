package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.data.repository.AuthRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.NotificationHistoryRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.ProductRepositoryImpl
import com.hkjj.heartbreakprice.data.repository.WishRepositoryImpl
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import com.hkjj.heartbreakprice.domain.repository.NotificationHistoryRepository
import com.hkjj.heartbreakprice.domain.repository.ProductRepository
import com.hkjj.heartbreakprice.domain.repository.WishRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }
    single<WishRepository> {
        WishRepositoryImpl()
    }
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
    single<NotificationHistoryRepository> {
        NotificationHistoryRepositoryImpl(get())
    }
}