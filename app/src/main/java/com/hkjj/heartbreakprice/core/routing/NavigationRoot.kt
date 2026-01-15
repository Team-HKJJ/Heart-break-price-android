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
import com.hkjj.heartbreakprice.data.repository.AuthRepositoryImpl
import com.hkjj.heartbreakprice.presentation.screen.main.MainRoot
import com.hkjj.heartbreakprice.presentation.screen.signin.SignInRoot
import org.koin.compose.koinInject

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController(),
    authRepository: AuthRepositoryImpl = koinInject<AuthRepositoryImpl>()
) {
    val isSignIn by authRepository.isSignIn.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "entry",
        modifier = Modifier.safeContentPadding()
    ) {

        composable("entry") {
            LaunchedEffect(isSignIn) {
                val target = if (isSignIn) {
                    "main"
                } else {
                    "sign_in"
                }

                navController.navigate(target) {
                    popUpTo("entry") { inclusive = true }
                }
            }
        }
        /* ===================== */
        /* ===== AUTH FLOW ===== */
        /* ===================== */
        composable("sign_in") {
            SignInRoot()
        }
        composable("sign_up") { /* 기존 코드 */ }

        /* ===================== */
        /* ===== MAIN FLOW ===== */
        /* ===================== */
        composable("main") {
            MainRoot(
                onNavigateToSubFirst = {
                    /* 상세 화면 넘어갈때 사용하는 콜백 */
                    //navController.navigate("detail/$it")
                },
                onNavigateToSubSecond = {  },
                onNavigateToSubThird = {  },
                onNavigateToSubFourth = {  },
            )
        }
        composable("detail/{param}") {
            Text("상세화면")
        }
    }
}