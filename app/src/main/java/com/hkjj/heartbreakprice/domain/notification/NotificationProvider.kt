package com.hkjj.heartbreakprice.domain.notification

interface NotificationProvider {
    suspend fun getFcmToken(): String?
}