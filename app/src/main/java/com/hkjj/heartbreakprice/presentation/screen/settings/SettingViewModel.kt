package com.hkjj.heartbreakprice.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.presentation.shopping.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _action = MutableSharedFlow<SettingAction>()
    val action = _action.asSharedFlow()

    init {
        // In a real app, you would fetch the user from a repository
        _uiState.value = SettingUiState(user = User(name = "MisterJerry", email = "misterjerry@example.com"))
    }

    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.OnLogoutClick -> {
                logout()
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            // Perform logout logic here, e.g., clearing session, etc.
            _action.emit(SettingAction.Logout)
        }
    }
}
