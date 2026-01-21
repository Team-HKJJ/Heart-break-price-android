package com.hkjj.heartbreakprice.core.routing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<NavigationEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeAuthStatus()
    }

    fun onAction(action: NavigationAction) {
        viewModelScope.launch {
            when (action) {
                is NavigationAction.NavigateToMain -> {
                    _event.send(NavigationEvent.NavigateTo(Route.Main(action.initialTab)))
                }
                is NavigationAction.NavigateToSignIn -> {
                    _event.send(NavigationEvent.NavigateTo(Route.SignIn))
                }
                is NavigationAction.NavigateToSignUp -> {
                    _event.send(NavigationEvent.NavigateTo(Route.SignUp))
                }
                is NavigationAction.NavigateToDetail -> {
                    _event.send(NavigationEvent.NavigateTo(Route.Detail(action.id)))
                }
                is NavigationAction.NavigateBack -> {
                    _event.send(NavigationEvent.NavigateBack)
                }
            }
        }
    }

    private fun observeAuthStatus() {
        viewModelScope.launch {
            authRepository.isSignIn.collect { isSignIn ->
                _uiState.update { it.copy(isSignIn = isSignIn) }
            }
        }
    }
}