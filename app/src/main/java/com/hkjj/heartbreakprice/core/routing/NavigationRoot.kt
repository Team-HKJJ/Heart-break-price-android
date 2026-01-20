package com.hkjj.heartbreakprice.core.routing

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hkjj.heartbreakprice.presentation.screen.main.MainRoot
import com.hkjj.heartbreakprice.presentation.screen.signin.SignInRoot
import com.hkjj.heartbreakprice.presentation.screen.signup.SignUpRoot
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController(),
    viewModel: NavigationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is NavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Route.Entry,
    ) {

        composable<Route.Entry> {
            LaunchedEffect(uiState.isSignIn) {
                val target: Route = if (uiState.isSignIn) {
                    Route.Main
                } else {
                    Route.SignIn
                }

                navController.navigate(target) {
                    popUpTo(Route.Entry) { inclusive = true }
                }
            }
        }
        /* ===================== */
        /* ===== AUTH FLOW ===== */
        /* ===================== */
        composable<Route.SignIn> {
            SignInRoot(
                onNavigationAction = viewModel::onAction
            )
        }
        composable<Route.SignUp> {
            SignUpRoot(
                onNavigationAction = viewModel::onAction
            )
        }

        /* ===================== */
        /* ===== MAIN FLOW ===== */
        /* ===================== */
        composable<Route.Main> {
            MainRoot(
                onNavigationAction = viewModel::onAction
            )
        }
        composable<Route.Detail> { backStackEntry ->
            val detail: Route.Detail = backStackEntry.toRoute()
            Text("상세화면: ${detail.param}")
        }
    }
}
