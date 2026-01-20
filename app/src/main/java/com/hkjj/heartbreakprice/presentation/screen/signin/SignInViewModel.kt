package com.hkjj.heartbreakprice.presentation.screen.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.usecase.LoginUseCase
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import com.hkjj.heartbreakprice.domain.usecase.UpdateFcmTokenUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
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
                 updateToken()
                 _event.send(SignInEvent.NavigateToMain)

            // Simulate network delay
//             delay(500)

//             // Mock login logic
//             if (email == "demo@example.com" && password == "demo1234") {
//                 authRepository.signIn()
//                 // TODO 로그인 성공 시 FCM 토큰 저장
//                 updateToken()
//                 _event.send(SignInEvent.NavigateToMain)

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

    private fun updateToken() {
        viewModelScope.launch {
            when (val result = updateFcmTokenUseCase()) {
                is Result.Success -> Log.d("FCM_LOG", "토큰 업데이트 완료")
                is Result.Error -> Log.e("FCM_LOG", "토큰 업데이트 실패: ${result.error.message}")
            }
        }
    }
}