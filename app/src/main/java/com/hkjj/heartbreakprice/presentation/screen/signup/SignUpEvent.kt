package com.hkjj.heartbreakprice.presentation.screen.signup

sealed interface SignUpEvent {
    data object NavigateToLogin : SignUpEvent
}
