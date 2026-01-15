package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.data.data_source.MockNotificationHistoryDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.MockProductDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import org.koin.dsl.module

val datasourceModule = module {
    single<ProductDataSource> {
        MockProductDataSourceImpl()
    }
    single<NotificationHistoryDataSource> {
        MockNotificationHistoryDataSourceImpl()
    }
}