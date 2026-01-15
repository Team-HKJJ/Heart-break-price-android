package com.hkjj.heartbreakprice.presentation.screen.wish

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun WishRoot(viewModel: WishViewModel = koinViewModel() ) {
    val state = viewModel.uiState.collectAsState()

    WishScreen(
        state = state.value,
        onRemove = {},
        onUpdateTargetPrice ={_,_->},
    )

}

