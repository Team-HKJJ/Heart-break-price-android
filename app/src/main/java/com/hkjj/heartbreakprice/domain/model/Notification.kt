package com.hkjj.heartbreakprice.domain.model

import java.util.Date

data class Notification(
    val id: String,
    val type: NotificationType,
    val productName: String,
    val productImage: String,
    val message: String,
    val timestamp: Date,
    val isRead: Boolean,
    val oldPrice: Int? = null,
    val newPrice: Int? = null
)

enum class NotificationType {
    PRICE_DROP,
    TARGET_REACHED,
    BACK_IN_STOCK
}