package com.hkjj.heartbreakprice.presentation.screen.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingRoot(
    viewModel: SettingViewModel = koinViewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SettingEvent.Logout -> {
                    onLogout()
                }
            }
        }
    }

    SettingScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}
