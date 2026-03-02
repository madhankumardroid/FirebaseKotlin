package com.madhan.firebaseauthentication.presentation.forgot_password

import com.madhan.firebaseauthentication.presentation.common.ViewEvent
import com.madhan.firebaseauthentication.presentation.common.ViewSideEffect
import com.madhan.firebaseauthentication.presentation.common.ViewState

class ForgotPasswordContract {
    sealed class Event : ViewEvent {
        data class EmailChanged(val email: String) : Event()
        object SendResetLink : Event()
        object BackToLogin : Event()
    }

    data class State(
        val email: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val isEmailSent: Boolean = false
    ) : ViewState

    sealed class SideEffect : ViewSideEffect {
        object NavigateBack : SideEffect()
    }
}
