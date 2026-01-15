package com.hkjj.heartbreakprice.data.data_source

import com.hkjj.heartbreakprice.domain.model.Notification
import com.hkjj.heartbreakprice.domain.model.NotificationType
import java.util.Date

class MockNotificationHistoryDataSourceImpl : NotificationHistoryDataSource {
    val now = Date()
    val mockNotifications = listOf(
        Notification(
            id = "1",
            type = NotificationType.TARGET_REACHED,
            productName = "애플 에어팟 프로 2세대",
            productImage = "https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7?w=400",
            message = "목표가에 도달했습니다!",
            timestamp = Date(now.time - 3600000), // 1 hour ago
            isRead = false,
            oldPrice = 359000,
            newPrice = 289000
        ),
        Notification(
            id = "2",
            type = NotificationType.PRICE_DROP,
            productName = "삼성 갤럭시 버즈2 프로",
            productImage = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400",
            message = "가격이 15% 하락했습니다",
            timestamp = Date(now.time - 7200000), // 2 hours ago
            isRead = false,
            oldPrice = 229000,
            newPrice = 194000
        ),
        Notification(
            id = "3",
            type = NotificationType.PRICE_DROP,
            productName = "LG 그램 17인치 노트북",
            productImage = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400",
            message = "가격이 10% 하락했습니다",
            timestamp = Date(now.time - 86400000), // 1 day ago
            isRead = true,
            oldPrice = 2190000,
            newPrice = 1971000
        )
    )

    override suspend fun getAllNotificationHistories(): List<Notification> {
        return mockNotifications
    }

    override suspend fun readAsMarkNotification(id: String) {
        mockNotifications.map { if (it.id == id) it.copy(isRead = !it.isRead) else it }
    }
}