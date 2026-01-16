package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.BuildConfig
import com.hkjj.heartbreakprice.data.data_source.local.MockNotificationHistoryDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.local.MockProductDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.data_source.remote.RemoteProductDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.remote.api.NaverShoppingApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val datasourceModule = module {
    single<NotificationHistoryDataSource> {
        MockNotificationHistoryDataSourceImpl()
    }

    if (BuildConfig.FLAVOR == "prod") {
        single {
            Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(NaverShoppingApi::class.java)
        }
        single<ProductDataSource> { RemoteProductDataSourceImpl(get()) }
    } else {
        single<ProductDataSource> { MockProductDataSourceImpl() }
    }
}