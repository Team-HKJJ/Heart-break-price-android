package com.hkjj.heartbreakprice.presentation.screen.signup

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)
