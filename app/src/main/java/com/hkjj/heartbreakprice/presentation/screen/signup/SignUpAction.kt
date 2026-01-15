package com.hkjj.heartbreakprice.presentation.screen.signup

sealed interface SignUpAction {
    data class OnNameChange(val name: String) : SignUpAction
    data class OnEmailChange(val email: String) : SignUpAction
    data class OnPasswordChange(val password: String) : SignUpAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : SignUpAction
    data object OnSignUpClick : SignUpAction
    data object OnLoginClick : SignUpAction
}
