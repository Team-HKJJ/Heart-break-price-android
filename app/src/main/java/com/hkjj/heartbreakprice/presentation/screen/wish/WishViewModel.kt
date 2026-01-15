package com.hkjj.heartbreakprice.presentation.screen.wish

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.core.util.TimeUtil
import com.hkjj.heartbreakprice.domain.model.Product
import com.hkjj.heartbreakprice.domain.model.WishProduct
import com.hkjj.heartbreakprice.domain.usecase.AddWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.DeleteWishUseCase
import com.hkjj.heartbreakprice.domain.usecase.GetWishesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            addedDate = TimeUtil.generateTime()
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

    fun deleteWish(id: String) {
        _uiState.update {
            it.copy(wishProducts = it.wishProducts.filter { item -> item.id != id })
        }
        viewModelScope.launch {
            deleteWishUseCase.invoke(id)
        }
    }

    fun getWishes() {
        viewModelScope.launch {
            when (val result = getWishesUseCase.invoke()) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(errorMsg = result.error.message)
                    }
                }

                is Result.Success -> {
                    _uiState.update {
                        it.copy(wishProducts = result.data)
                    }
                }
            }
        }
    }
    fun showDialog(){
        _uiState.update {
            it.copy(isDialogShow = true)
        }
    }
}

