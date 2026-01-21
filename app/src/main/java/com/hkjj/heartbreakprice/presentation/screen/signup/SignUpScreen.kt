package com.hkjj.heartbreakprice.presentation.screen.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hkjj.heartbreakprice.R
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
            .background(AppColors.Background)
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
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                tint = AppColors.Unspecified,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AppColors.Gray900
            )
            Text(
                stringResource(R.string.sign_up_title),
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.Gray600
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, AppColors.Gray100),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(32.dp)) {
                    Text(
                        stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Gray900,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    if (state.errorMessage.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppColors.Error.copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = AppColors.Error,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                state.errorMessage,
                                color = AppColors.Error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    if (state.isSuccess) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    AppColors.Success.copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AppColors.Success,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(R.string.sign_up_success),
                                color = AppColors.Success,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    // Name
                    Text(
                        stringResource(R.string.name),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { onAction(SignUpAction.OnNameChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = AppColors.Gray400,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        placeholder = { 
                            Text(
                                stringResource(R.string.sign_up_name_placeholder),
                                fontSize = 14.sp,
                                color = AppColors.Gray400
                            ) 
                        },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            focusedLabelColor = AppColors.Primary,
                            cursorColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.Gray300,
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Text(
                        stringResource(R.string.email),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(SignUpAction.OnEmailChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = AppColors.Gray400,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        placeholder = { 
                            Text(
                                stringResource(R.string.sign_in_email_placeholder),
                                fontSize = 14.sp,
                                color = AppColors.Gray400
                            ) 
                        },
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            focusedLabelColor = AppColors.Primary,
                            cursorColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.Gray300,
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Text(
                        stringResource(R.string.password),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onAction(SignUpAction.OnPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = AppColors.Gray400,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        placeholder = { 
                            Text(
                                stringResource(R.string.sign_in_password_placeholder),
                                fontSize = 14.sp,
                                color = AppColors.Gray400
                            ) 
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            focusedLabelColor = AppColors.Primary,
                            cursorColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.Gray300,
                        )
                    )
                    Text(
                        stringResource(R.string.password_rule),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.Gray500,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    Text(
                        stringResource(R.string.confirm_password),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { onAction(SignUpAction.OnConfirmPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = AppColors.Gray400,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        placeholder = { 
                            Text(
                                stringResource(R.string.sign_up_confirm_password_placeholder),
                                fontSize = 14.sp,
                                color = AppColors.Gray400
                            ) 
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            focusedLabelColor = AppColors.Primary,
                            cursorColor = AppColors.Primary,
                            unfocusedBorderColor = AppColors.Gray300,
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onAction(SignUpAction.OnSignUpClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !state.isLoading && !state.isSuccess,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
                    ) {
                        Text(
                            if (state.isLoading) stringResource(R.string.signing_up) 
                            else if (state.isSuccess) stringResource(R.string.sign_up_done) 
                            else stringResource(R.string.sign_up)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.already_have_account),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppColors.Gray500
                        )
                        TextButton(
                            onClick = { onAction(SignUpAction.OnLoginClick) },
                            enabled = !state.isLoading && !state.isSuccess
                        ) {
                            Text(stringResource(R.string.sign_in), color = AppColors.Primary)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        state = SignUpUiState(),
        onAction = {}
    )
}