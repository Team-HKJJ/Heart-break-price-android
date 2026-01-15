package com.hkjj.heartbreakprice.core.routing

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Entry : Route

    @Serializable
    data object SignIn : Route

    @Serializable
    data object SignUp : Route

    @Serializable
    data object Main : Route

    @Serializable
    data class Detail(val param: String) : Route
}