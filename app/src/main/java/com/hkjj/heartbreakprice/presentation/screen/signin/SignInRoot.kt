package com.hkjj.heartbreakprice.presentation.screen.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignInRoot(onNavigateSignUp: () -> Unit) {
    Column {
        Text("SignIn")
        Button(onClick = {}) {
            Text("SignIn")
        }
        Button(onClick = onNavigateSignUp) {
            Text("SignUp")
        }
    }
}