package com.hkjj.heartbreakprice.core.di

import com.google.firebase.messaging.FirebaseMessaging
import com.hkjj.heartbreakprice.domain.notification.FcmProviderImpl
import com.hkjj.heartbreakprice.domain.notification.NotificationProvider
import org.koin.dsl.module

val appModule = module {
    single { FirebaseMessaging.getInstance() }
    single<NotificationProvider> { FcmProviderImpl(get()) }
}