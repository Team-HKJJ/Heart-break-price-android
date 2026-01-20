package com.hkjj.heartbreakprice.domain.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FcmProviderImpl(
    private val messaging: FirebaseMessaging
) : NotificationProvider {

    override suspend fun getFcmToken(): String? = suspendCancellableCoroutine { continuation ->
        messaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) continuation.resume(task.result)
            else continuation.resume(null)
        }
    }
}