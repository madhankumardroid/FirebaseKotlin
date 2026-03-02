package com.madhan.firebaseauthentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.madhan.firebaseauthentication.presentation.forgot_password.ForgotPasswordScreen
import com.madhan.firebaseauthentication.presentation.home.HomeScreen
import com.madhan.firebaseauthentication.presentation.login.LoginScreen
import com.madhan.firebaseauthentication.presentation.signup.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Login> {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignUp) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo<Screen.Login> { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.SignUp> {
            SignUpScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home) {
                        popUpTo<Screen.Login> { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.Home> {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login) {
                        popUpTo<Screen.Home> { inclusive = true }
                    }
                }
            )
        }
    }
}
