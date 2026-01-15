package com.hkjj.heartbreakprice.presentation.screen.signin

sealed interface SignInAction {
    data class OnEmailChange(val email: String) : SignInAction
    data class OnPasswordChange(val password: String) : SignInAction
    data object OnLoginClick : SignInAction
    data object OnSignUpClick : SignInAction
}
