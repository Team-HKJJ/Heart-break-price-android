package com.hkjj.heartbreakprice.presentation.screen.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.ui.AppColors

@Composable
fun SignUpScreen(
    state: SignUpUiState,
    onAction: (SignUpAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(AppColors.PrimaryLight, AppColors.Primary.copy(alpha = 0.2f))
                )
            )
            .systemBarsPadding()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(AppColors.Primary, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = AppColors.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "심쿵가",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Gray900
            )
            Text(
                "계정을 만들고 시작하세요",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Gray600
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        "회원가입",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Gray900,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (state.errorMessage.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppColors.Error.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = AppColors.Error, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(state.errorMessage, color = AppColors.Error, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    if (state.isSuccess) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(AppColors.Success.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AppColors.Success, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("회원가입이 완료되었습니다! 로그인 페이지로 이동합니다...", color = AppColors.Success, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    // Name
                    Text("이름", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { onAction(SignUpAction.OnNameChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = AppColors.Gray500) },
                        placeholder = { Text("홍길동") },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Text("이메일", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(SignUpAction.OnEmailChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = AppColors.Gray500) },
                        placeholder = { Text("example@email.com") },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Text("비밀번호", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onAction(SignUpAction.OnPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AppColors.Gray500) },
                        placeholder = { Text("••••••••") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )
                    Text("최소 6자 이상", style = MaterialTheme.typography.bodySmall, color = AppColors.Gray500, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    Text("비밀번호 확인", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { onAction(SignUpAction.OnConfirmPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AppColors.Gray500) },
                        placeholder = { Text("••••••••") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onAction(SignUpAction.OnSignUpClick) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
                    ) {
                        Text(if (state.isLoading) "가입 중..." else if (state.isSuccess) "완료!" else "회원가입")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("이미 계정이 있으신가요?", style = MaterialTheme.typography.bodySmall, color = AppColors.Gray500)
                        TextButton(onClick = { onAction(SignUpAction.OnLoginClick) }, enabled = !state.isLoading && !state.isSuccess) {
                            Text("로그인", color = AppColors.Primary)
                        }
                    }
                }
            }
        }
    }
}
