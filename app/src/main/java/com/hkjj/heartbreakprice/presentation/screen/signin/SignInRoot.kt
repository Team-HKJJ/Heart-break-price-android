package com.hkjj.heartbreakprice.presentation.screen.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hkjj.heartbreakprice.core.routing.NavigationAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInRoot(
    onNavigationAction: (NavigationAction) -> Unit,
    viewModel: SignInViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SignInEvent.NavigateToMain -> {
                    onNavigationAction(NavigationAction.NavigateToMain())
                }
                is SignInEvent.NavigateToSignUp -> {
                    onNavigationAction(NavigationAction.NavigateToSignUp)
                }
            }
        }
    }

    SignInScreen(
        state = uiState,
        onAction = viewModel::onAction
    )
}