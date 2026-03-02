package com.madhan.firebaseauthentication.presentation.login

import com.madhan.firebaseauthentication.presentation.common.ViewEvent
import com.madhan.firebaseauthentication.presentation.common.ViewSideEffect
import com.madhan.firebaseauthentication.presentation.common.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        object Login : Event()
        object SignUp : Event()
        object ForgotPassword : Event()
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class SideEffect : ViewSideEffect {
        object NavigateToHome : SideEffect()
        object NavigateToSignUp : SideEffect()
        object NavigateToForgotPassword : SideEffect()
    }
}
