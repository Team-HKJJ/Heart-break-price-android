package com.hkjj.heartbreakprice.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<SignUpEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.OnNameChange -> {
                _uiState.update { it.copy(name = action.name, errorMessage = "") }
            }
            is SignUpAction.OnEmailChange -> {
                _uiState.update { it.copy(email = action.email, errorMessage = "") }
            }
            is SignUpAction.OnPasswordChange -> {
                _uiState.update { it.copy(password = action.password, errorMessage = "") }
            }
            is SignUpAction.OnConfirmPasswordChange -> {
                _uiState.update { it.copy(confirmPassword = action.confirmPassword, errorMessage = "") }
            }
            is SignUpAction.OnSignUpClick -> {
                signUp()
            }
            is SignUpAction.OnLoginClick -> {
                viewModelScope.launch {
                    _event.send(SignUpEvent.NavigateToLogin)
                }
            }
        }
    }

    private fun signUp() {
        val state = uiState.value

        if (state.name.isEmpty() || state.email.isEmpty() || state.password.isEmpty() || state.confirmPassword.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "모든 필드를 입력해주세요.") }
            return
        }
        if (state.password.length < 6) {
            _uiState.update { it.copy(errorMessage = "비밀번호는 최소 6자 이상이어야 합니다.") }
            return
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "비밀번호가 일치하지 않습니다.") }
            return
        }
        if (!state.email.contains('@')) {
            _uiState.update { it.copy(errorMessage = "올바른 이메일 형식을 입력해주세요.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }
            delay(500) // Simulate network delay

            // Mock signup logic
            // In a real app, call repository here
            val isSuccess = true // Assume success

            if (isSuccess) {
                _uiState.update { it.copy(isSuccess = true, isLoading = false) }
                delay(1500)
                _event.send(SignUpEvent.NavigateToLogin)
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "이미 가입된 이메일입니다.") }
            }
        }
    }
}
