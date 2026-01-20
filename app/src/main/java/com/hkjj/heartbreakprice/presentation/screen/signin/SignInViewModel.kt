package com.hkjj.heartbreakprice.presentation.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.usecase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginUseCase: LoginUseCase
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

            val result = loginUseCase(email, password)
            if (result is Result.Success) {
                 _event.send(SignInEvent.NavigateToMain)
            } else {
                val exception = (result as Result.Error).error
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "로그인에 실패했습니다."
                    )
                }
            }
        }
    }
}