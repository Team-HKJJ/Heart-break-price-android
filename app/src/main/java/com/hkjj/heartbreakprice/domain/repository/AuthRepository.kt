package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isSignIn: Flow<Boolean>
    suspend fun login(email: String, password: String): Result<Unit, Exception>
    suspend fun signUp(email: String, password: String, name: String): Result<Unit, Exception>
    suspend fun logout()
    suspend fun getUser(): User
}
