package com.hkjj.heartbreakprice.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl {
    private val _isSignIn = MutableStateFlow(false)
    val isSignIn = _isSignIn.asStateFlow()
}