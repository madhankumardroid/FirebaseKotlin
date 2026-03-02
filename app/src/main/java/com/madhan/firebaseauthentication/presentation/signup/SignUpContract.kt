package com.madhan.firebaseauthentication.presentation.signup

import com.madhan.firebaseauthentication.presentation.common.ViewEvent
import com.madhan.firebaseauthentication.presentation.common.ViewSideEffect
import com.madhan.firebaseauthentication.presentation.common.ViewState

class SignUpContract {
    sealed class Event : ViewEvent {
        data class FirstNameChanged(val firstName: String) : Event()
        data class LastNameChanged(val lastName: String) : Event()
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        object SignUp : Event()
        object BackToLogin : Event()
    }

    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class SideEffect : ViewSideEffect {
        object NavigateToHome : SideEffect()
        object NavigateBack : SideEffect()
    }
}
