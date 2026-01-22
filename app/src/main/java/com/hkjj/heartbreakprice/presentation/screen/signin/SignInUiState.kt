package com.hkjj.heartbreakprice.presentation.screen.signin

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
