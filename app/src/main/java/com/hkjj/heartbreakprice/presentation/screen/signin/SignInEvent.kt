package com.hkjj.heartbreakprice.presentation.screen.signin

sealed interface SignInEvent {
    data object NavigateToMain : SignInEvent
    data object NavigateToSignUp : SignInEvent
}