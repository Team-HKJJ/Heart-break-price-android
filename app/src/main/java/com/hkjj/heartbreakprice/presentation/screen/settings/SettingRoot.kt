package com.hkjj.heartbreakprice.presentation.screen.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingRoot(
    viewModel: SettingViewModel = SettingViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.action) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is SettingAction.Logout -> {
                    onLogout()
                }
            }
        }
    }

    SettingScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}
