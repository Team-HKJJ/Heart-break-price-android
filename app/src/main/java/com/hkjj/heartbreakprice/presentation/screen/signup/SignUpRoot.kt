package com.hkjj.heartbreakprice.presentation.screen.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hkjj.heartbreakprice.core.routing.NavigationAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpRoot(
    onNavigationAction: (NavigationAction) -> Unit,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SignUpEvent.NavigateToLogin -> {
                    onNavigationAction(NavigationAction.NavigateBack)
                }
            }
        }
    }

    SignUpScreen(
        state = uiState,
        onAction = viewModel::onAction
    )
}
