package com.hkjj.heartbreakprice.presentation.screen.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationRoot(
    viewModel: NotificationViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NotificationScreen(
        uiState = state,
        onAction = viewModel::onAction
    )
}
