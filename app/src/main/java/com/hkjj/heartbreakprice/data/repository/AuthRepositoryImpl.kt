package com.hkjj.heartbreakprice.data.repository

import com.hkjj.heartbreakprice.domain.model.User
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl : AuthRepository {
    private val _isSignIn = MutableStateFlow(false)
    override val isSignIn = _isSignIn.asStateFlow()

    override suspend fun logout() {
        _isSignIn.value = false
    }

    override suspend fun getUser(): User {
        return User(
            name = "MisterJerry",
            email = "misterjerry@example.com",
            fcmToken = "test"
        )
    }
}
