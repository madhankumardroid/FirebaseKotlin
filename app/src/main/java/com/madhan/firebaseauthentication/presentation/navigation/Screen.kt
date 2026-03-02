package com.madhan.firebaseauthentication.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object SignUp : Screen

    @Serializable
    data object ForgotPassword : Screen

    @Serializable
    data object Home : Screen
}
