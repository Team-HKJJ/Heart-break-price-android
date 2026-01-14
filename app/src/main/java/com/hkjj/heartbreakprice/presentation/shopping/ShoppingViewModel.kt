package com.hkjj.heartbreakprice.presentation.shopping

import androidx.lifecycle.ViewModel
import com.hkjj.heartbreakprice.presentation.shopping.model.FavoriteProduct
import com.hkjj.heartbreakprice.presentation.shopping.model.Notification
import com.hkjj.heartbreakprice.presentation.shopping.model.NotificationType
import com.hkjj.heartbreakprice.presentation.shopping.model.Product
import com.hkjj.heartbreakprice.presentation.shopping.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingViewModel : ViewModel() {
    private val _favorites = MutableStateFlow<List<FavoriteProduct>>(emptyList())
    val favorites: StateFlow<List<FavoriteProduct>> = _favorites.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    init {
        // Mock Notifications
        val now = java.util.Date()
        _notifications.value = listOf(
            Notification(
                id = "1",
                type = NotificationType.TARGET_REACHED,
                productName = "애플 에어팟 프로 2세대",
                productImage = "https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7?w=400",
                message = "목표가에 도달했습니다!",
                timestamp = java.util.Date(now.time - 3600000), // 1 hour ago
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
                timestamp = java.util.Date(now.time - 7200000), // 2 hours ago
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
                timestamp = java.util.Date(now.time - 86400000), // 1 day ago
                isRead = true,
                oldPrice = 2190000,
                newPrice = 1971000
            )
        )
    }

    // Mock User DB: Email -> Name, Password
    private val mockUsers = mutableMapOf(
        "demo@example.com" to ("데모 사용자" to "demo1234")
    )

    fun login(email: String, password: String): Boolean {
        val account = mockUsers[email]
        return if (account != null && account.second == password) {
            _user.value = User(account.first, email)
            true
        } else {
            false
        }
    }

    fun signup(name: String, email: String, password: String): Boolean {
        if (mockUsers.containsKey(email)) {
            return false
        }
        mockUsers[email] = name to password
        return true
    }

    fun logout() {
        _user.value = null
        _favorites.value = emptyList()
    }

    fun addToFavorites(product: Product) {
        _favorites.update { currentFavorites ->
            if (currentFavorites.any { it.id == product.id }) {
                currentFavorites
            } else {
                currentFavorites + FavoriteProduct.fromProduct(product)
            }
        }
    }

    fun removeFromFavorites(productId: String) {
        _favorites.update { currentFavorites ->
            currentFavorites.filter { it.id != productId }
        }
    }

    fun updateTargetPrice(productId: String, targetPrice: Int) {
        _favorites.update { currentFavorites ->
            currentFavorites.map { fav ->
                if (fav.id == productId) {
                    fav.copy(targetPrice = targetPrice)
                } else {
                    fav
                }
            }
        }
    }

    fun isFavorite(productId: String): Boolean {
        return _favorites.value.any { it.id == productId }
    }

    fun markNotificationAsRead(notificationId: String) {
        _notifications.update { currentList ->
            currentList.map {
                if (it.id == notificationId) it.copy(isRead = true) else it
            }
        }
    }

    fun markAllNotificationsAsRead() {
        _notifications.update { currentList ->
            currentList.map { it.copy(isRead = true) }
        }
    }

    fun deleteNotification(notificationId: String) {
        _notifications.update { currentList ->
            currentList.filter { it.id != notificationId }
        }
    }
}
