package com.hkjj.heartbreakprice.presentation.shopping

import androidx.lifecycle.ViewModel
import com.hkjj.heartbreakprice.presentation.shopping.model.FavoriteProduct
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
}
