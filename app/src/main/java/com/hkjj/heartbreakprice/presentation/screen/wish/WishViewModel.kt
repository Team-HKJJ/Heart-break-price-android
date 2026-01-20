package com.hkjj.heartbreakprice.presentation.screen.wish

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.core.util.TimeUtil
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WishViewModel(
    private val addWishUseCase: AddWishUseCase,
    private val deleteWishUseCase: DeleteWishUseCase,
    private val getWishesUseCase: GetWishesUseCase,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(WishUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getWishes()
    }

    fun onAction(action: WishAction) {
        when (action) {
            WishAction.OnHideDialog -> hideDialog()
            is WishAction.OnShowDialog -> showDialog(action.id)
            is WishAction.OnTargetPriceChange -> updateTargetPrice(action.id, action.targetPrice)
            is WishAction.OnDeleteClick -> deleteWish(action.id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWish(product: Product) {
        val newWish = WishProduct(
            id = product.id,
            name = product.name,
            price = product.price,
            originalPrice = product.originalPrice,
            image = product.image,
            category = product.category,
            shop = product.shop,
            targetPrice = product.price,
            addedDate = TimeUtil.generateTime(),
            url=""
        )
        _uiState.update {
            it.copy(
                wishProducts = it.wishProducts + newWish
            )
        }
        viewModelScope.launch {
            addWishUseCase.invoke(newWish)
        }
    }

    private fun deleteWish(id: String) {
        _uiState.update {
            it.copy(wishProducts = it.wishProducts.filter { item -> item.id != id })
        }
        viewModelScope.launch {
            deleteWishUseCase.invoke(id)
        }
    }

    private fun getWishes() {
        viewModelScope.launch {
            getWishesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(errorMsg = e.message)
                    }
                }
                .collect { wishes ->
                    _uiState.update {
                        it.copy(wishProducts = wishes)
                    }
                }
        }
    }

    private fun updateTargetPrice(id: String, targetPrice: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                wishProducts = currentState.wishProducts.map { product ->
                    if (product.id == id) {
                        product.copy(targetPrice = targetPrice)
                    } else {
                        product
                    }
                },
                isDialogShow = false
            )
        }
    }

    private fun showDialog(id: String) {
        _uiState.update {
            it.copy(
                isDialogShow = true,
                targetId = id
            )
        }
    }

    private fun hideDialog() {
        _uiState.update {
            it.copy(
                isDialogShow = false,
                targetId = null
            )
        }
    }
}

