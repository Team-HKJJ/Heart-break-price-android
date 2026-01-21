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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.hkjj.heartbreakprice.R

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
                    colors = listOf(Color(0xFFEFF6FF), Color(0xFFE0E7FF))
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
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
            Text(
                stringResource(R.string.sign_up_title),
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
                        stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (state.errorMessage.isNotEmpty()) {
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
                            Text(state.errorMessage, color = Color(0xFFB91C1C), style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    if (state.isSuccess) {
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
                            Text(stringResource(R.string.sign_up_success), color = Color(0xFF166534), style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    // Name
                    Text(stringResource(R.string.name), style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { onAction(SignUpAction.OnNameChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text(stringResource(R.string.name_hint)) },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Text(stringResource(R.string.email), style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(SignUpAction.OnEmailChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text(stringResource(R.string.email_hint)) },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Text(stringResource(R.string.password), style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onAction(SignUpAction.OnPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text(stringResource(R.string.password_hint)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess
                    )
                    Text(stringResource(R.string.password_rule), style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    Text(stringResource(R.string.confirm_password), style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 4.dp))
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { onAction(SignUpAction.OnConfirmPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
                        placeholder = { Text(stringResource(R.string.password_hint)) },
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
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                    ) {
                        Text(if (state.isLoading) stringResource(R.string.signing_up) else if (state.isSuccess) stringResource(R.string.sign_up_done) else stringResource(R.string.sign_up))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.already_have_account), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        TextButton(onClick = { onAction(SignUpAction.OnLoginClick) }, enabled = !state.isLoading && !state.isSuccess) {
                            Text(stringResource(R.string.sign_in), color = Color(0xFF2563EB))
                        }
                    }
                }
            }
        }
    }
}
