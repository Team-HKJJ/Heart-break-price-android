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
import com.hkjj.heartbreakprice.data.repository.AuthRepositoryImpl
import com.hkjj.heartbreakprice.presentation.screen.main.MainRoot
import com.hkjj.heartbreakprice.presentation.screen.signin.SignInRoot
import com.hkjj.heartbreakprice.presentation.screen.signup.SignUpRoot
import org.koin.compose.koinInject

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController(),
    authRepository: AuthRepositoryImpl = koinInject<AuthRepositoryImpl>()
) {
    val isSignIn by authRepository.isSignIn.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Route.Entry,
        modifier = Modifier.safeContentPadding()
    ) {

        composable<Route.Entry> {
            LaunchedEffect(isSignIn) {
                val target: Route = if (isSignIn) {
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
            SignInRoot(onNavigateSignUp = { navController.navigate(Route.SignUp) })
        }
        composable<Route.SignUp> {
            SignUpRoot()
        }

        /* ===================== */
        /* ===== MAIN FLOW ===== */
        /* ===================== */
        composable<Route.Main> {
            MainRoot(
                onNavigateToSubFirst = {
                    /* 상세 화면 넘어갈때 사용하는 콜백 */
                    //navController.navigate(Route.Detail(it))
                },
                onNavigateToSubSecond = {  },
                onNavigateToSubThird = {  },
                onNavigateToSubFourth = {  },
            )
        }
        composable<Route.Detail> { backStackEntry ->
            val detail: Route.Detail = backStackEntry.toRoute()
            Text("상세화면: ${detail.param}")
        }
    }
}