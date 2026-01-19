package com.hkjj.heartbreakprice.core.di

import com.hkjj.heartbreakprice.BuildConfig
import com.hkjj.heartbreakprice.data.data_source.local.MockNotificationHistoryDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.local.MockProductDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.NotificationHistoryDataSource
import com.hkjj.heartbreakprice.data.data_source.ProductDataSource
import com.hkjj.heartbreakprice.data.data_source.remote.RemoteProductDataSourceImpl
import com.hkjj.heartbreakprice.data.data_source.remote.api.NaverShoppingApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val datasourceModule = module {
    single<NotificationHistoryDataSource> {
        MockNotificationHistoryDataSourceImpl()
    }

    if (BuildConfig.FLAVOR == "prod") {
        single {
            val json = Json {
                ignoreUnknownKeys = true // DTO에 정의되지 않은 필드는 무시
                coerceInputValues = true // null이 올 수 없는 필드에 null이 오면 기본값 사용
            }
            val contentType = "application/json".toMediaType()
            Retrofit.Builder()
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(ScalarsConverterFactory.create()) // String 응답 처리용 (기존 유지)
                .addConverterFactory(json.asConverterFactory(contentType)) // JSON 자동 변환용 (신규 추가)
                .build()
                .create(NaverShoppingApi::class.java)
        }
        single<ProductDataSource> { RemoteProductDataSourceImpl(get()) }
    } else {
        single<ProductDataSource> { MockProductDataSourceImpl() }
    }
}