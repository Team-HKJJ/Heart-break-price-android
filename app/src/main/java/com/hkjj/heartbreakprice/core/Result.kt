package com.hkjj.heartbreakprice.core

sealed class Result<T, E> {
    data class Success<T>(
        val data: T
    ) : Result<T, Nothing>()

    data class Error<E>(
        val error: E
    ) : Result<Nothing, E>()
}