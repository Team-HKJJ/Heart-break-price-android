package com.hkjj.heartbreakprice.core.network

sealed class ApiResponse<out T> {
    data class Success<T>(
        val statusCode: Int,
        val headers: Map<String, List<String>>,
        val body: T
    ) : ApiResponse<T>()

    data class Failure(
        val statusCode: Int,
        val headers: Map<String, List<String>>,
        val errorBody: String?
    ) : ApiResponse<Nothing>()

    data class NetworkError(
        val throwable: Throwable
    ) : ApiResponse<Nothing>()
}
