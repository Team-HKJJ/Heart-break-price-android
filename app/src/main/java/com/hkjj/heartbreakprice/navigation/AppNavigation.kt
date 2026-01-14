package com.hkjj.heartbreakprice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hkjj.heartbreakprice.presentation.auth.LoginScreen
import com.hkjj.heartbreakprice.presentation.auth.SignupScreen
import com.hkjj.heartbreakprice.presentation.shopping.ShoppingScreen
import com.hkjj.heartbreakprice.presentation.shopping.ShoppingViewModel

@Composable
fun AppNavigation(
    viewModel: ShoppingViewModel = viewModel()
) {
    val navController = rememberNavController()
    val user by viewModel.user.collectAsState()

    // Redirect to Main if user is logged in, else mock simple logic for now. 
    // In a real app, startDestination would be determined by auth state.
    // Here we handle it via logic or key.
    
    // We will use "login" as start destination.
    // If user is already logged in (persisted), we'd start at Main. 
    // For now, simple mock starts at Login.

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            if (user != null) {
                // If user state changes to logged in, navigate to Main
                // This is a side-effect, usually better handled in LaunchedEffect, but strictly composable:
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }
            
            LoginScreen(
                onLogin = { email, password ->
                     val success = viewModel.login(email, password)
                     if (success) {
                         navController.navigate(Screen.Main.route) {
                             popUpTo(Screen.Login.route) { inclusive = true }
                         }
                     }
                     success
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }
        
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignup = { name, email, password ->
                    viewModel.signup(name, email, password)
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        
        composable(Screen.Main.route) {
            if (user == null) {
                 androidx.compose.runtime.LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            }
            
            ShoppingScreen(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
