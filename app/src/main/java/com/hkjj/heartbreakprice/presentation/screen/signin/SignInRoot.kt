package com.hkjj.heartbreakprice.presentation.screen.signin

import androidx.compose.runtime.Composable

@Composable
fun SignInRoot(
    onNavigateSignUp: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    SignInScreen(
        onLogin = { email, password -> email == "demo@example.com" && password == "demo1234" },
        onNavigateToSignup = { onNavigateSignUp() },
        onNavigateToMain = { onNavigateToMain() },
    )
}