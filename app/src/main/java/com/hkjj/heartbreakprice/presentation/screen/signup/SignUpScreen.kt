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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    onSignup: (String, String, String) -> Boolean,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFEFF6FF), Color(0xFFE0E7FF))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFF2563EB), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "가격 추적 쇼핑",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                "계정을 만들고 시작하세요",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4B5563)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        "회원가입",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (error.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFEE2E2), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(error, color = Color(0xFFB91C1C), style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    if (success) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0FDF4), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("회원가입이 완료되었습니다! 로그인 페이지로 이동합니다...", color = Color(0xFF166534), style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    // Name
                    Text("이름", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text("홍길동") },
                        singleLine = true,
                        enabled = !isLoading && !success
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Text("이메일", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text("example@email.com") },
                        singleLine = true,
                        enabled = !isLoading && !success
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Text("비밀번호", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text("••••••••") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !isLoading && !success
                    )
                    Text("최소 6자 이상", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    Text("비밀번호 확인", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text("••••••••") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !isLoading && !success
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                                error = "모든 필드를 입력해주세요."
                                return@Button
                            }
                            if (password.length < 6) {
                                error = "비밀번호는 최소 6자 이상이어야 합니다."
                                return@Button
                            }
                            if (password != confirmPassword) {
                                error = "비밀번호가 일치하지 않습니다."
                                return@Button
                            }
                            if (!email.contains('@')) {
                                error = "올바른 이메일 형식을 입력해주세요."
                                return@Button
                            }

                            isLoading = true
                            error = ""
                            scope.launch {
                                delay(500)
                                if (onSignup(name, email, password)) {
                                    success = true
                                    delay(1500)
                                    onNavigateToLogin()
                                } else {
                                    error = "이미 가입된 이메일입니다."
                                }
                                isLoading = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading && !success,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                    ) {
                        Text(if (isLoading) "가입 중..." else if (success) "완료!" else "회원가입")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("이미 계정이 있으신가요?", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        TextButton(onClick = onNavigateToLogin, enabled = !isLoading && !success) {
                            Text("로그인", color = Color(0xFF2563EB))
                        }
                    }
                }
            }
        }
    }
}