package com.hkjj.heartbreakprice.domain.repository

import com.hkjj.heartbreakprice.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isSignIn: Flow<Boolean>
    suspend fun signIn()
    suspend fun logout()
    suspend fun getUser(): User
    suspend fun updateFcmToken(token: String)
}
