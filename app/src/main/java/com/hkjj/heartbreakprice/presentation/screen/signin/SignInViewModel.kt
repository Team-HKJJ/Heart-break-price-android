package com.hkjj.heartbreakprice.presentation.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<SignInEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.OnEmailChange -> {
                _uiState.update { it.copy(email = action.email, errorMessage = "") }
            }

            is SignInAction.OnPasswordChange -> {
                _uiState.update { it.copy(password = action.password, errorMessage = "") }
            }

            is SignInAction.OnLoginClick -> {
                login()
            }

            is SignInAction.OnSignUpClick -> {
                viewModelScope.launch {
                    _event.send(SignInEvent.NavigateToSignUp)
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val email = uiState.value.email
            val password = uiState.value.password

            if (email.isEmpty() || password.isEmpty()) {
                _uiState.update { it.copy(errorMessage = "이메일과 비밀번호를 입력해주세요.") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = "") }

            // Simulate network delay
            delay(500)

            // Mock login logic
            if (email == "demo@example.com" && password == "demo1234") {
                // authRepository.setSignIn(true) // Assuming repository has a method to update state
                // For now, since AuthRepositoryImpl only has a flow, we'll just emit success event
                // Ideally, AuthRepository should have a login method.
                // Assuming NavigationRoot observes AuthRepository state change, we might not need an explicit event if the repo updates the state.
                // However, following the requested pattern:
                _event.send(SignInEvent.NavigateToMain)
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "이메일 또는 비밀번호가 올바르지 않습니다."
                    )
                }
            }
        }
    }
}