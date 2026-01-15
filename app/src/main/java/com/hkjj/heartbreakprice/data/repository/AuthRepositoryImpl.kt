package com.hkjj.heartbreakprice.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl {
    private val _isSignIn = MutableStateFlow(true)
    val isSignIn = _isSignIn.asStateFlow()
}