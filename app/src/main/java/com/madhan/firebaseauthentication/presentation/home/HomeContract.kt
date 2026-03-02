package com.madhan.firebaseauthentication.presentation.home

import com.madhan.firebaseauthentication.domain.model.User
import com.madhan.firebaseauthentication.presentation.common.ViewEvent
import com.madhan.firebaseauthentication.presentation.common.ViewSideEffect
import com.madhan.firebaseauthentication.presentation.common.ViewState

class HomeContract {
    sealed class Event : ViewEvent {
        object LoadUserData : Event()
        object Logout : Event()
    }

    data class State(
        val user: User? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    ) : ViewState

    sealed class SideEffect : ViewSideEffect {
        object NavigateToLogin : SideEffect()
    }
}
