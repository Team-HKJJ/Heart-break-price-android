package com.hkjj.heartbreakprice.data.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hkjj.heartbreakprice.MainActivity
import com.hkjj.heartbreakprice.domain.usecase.GetSignInStatusUseCase
import com.hkjj.heartbreakprice.domain.usecase.UpdateFcmTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationMessagingService : FirebaseMessagingService(), KoinComponent {

    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase by inject()
    private val getSignInStatusUseCase: GetSignInStatusUseCase by inject()

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // 새로운 토큰이 생성될 때마다 서버에 업데이트해야 함
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_LOG", "Refreshed token: $token")

        // TODO: Repository를 통해 백엔드 서버나 Firestore에 토큰 저장
        serviceScope.launch {
            if (getSignInStatusUseCase()) {
                updateFcmTokenUseCase(token)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        serviceScope.launch {
            val isSignIn = getSignInStatusUseCase()
            if (!isSignIn) {
                Log.d("FCM_LOG", "로그인 상태가 아니므로 알림을 무시합니다.")
                return@launch
            }

            Log.d("FCM_LOG", "onMessageReceived 데이터: ${message.data}")

            val title = message.notification?.title ?: message.data["title"] ?: "가격 알림"
            val body = message.notification?.body ?: message.data["body"] ?: "목표가에 도달했습니다!"

            Log.d("FCM_LOG", "메시지 수신 성공: $body")

            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, messageBody: String) {
        val notificationId = System.currentTimeMillis().toInt()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 알림 클릭 시 앱 실행을 위한 Intent
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("navigate_to", "notification")
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 디자인
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // 기본 아이콘 사용
            .setContentTitle(title)
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // 중요도 높음
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        // 실제로 알림 띄우기
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "heart_break_price_channel_v1"
        const val NOTIFICATION_CHANNEL_NAME = "가격 하락 알림"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "설정한 목표가보다 가격이 낮아지면 알림을 보냅니다."
    }
}