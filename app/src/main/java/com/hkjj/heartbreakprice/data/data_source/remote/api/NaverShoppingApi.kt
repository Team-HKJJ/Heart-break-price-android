package com.hkjj.heartbreakprice.data.data_source.remote.api

import com.hkjj.heartbreakprice.data.dto.NaverShoppingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverShoppingApi {
    @GET("v1/search/shop.json")
    suspend fun searchProducts(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int = 30,
        @Query("start") start: Int = 1
    ): Response<NaverShoppingResponseDto>
}