package com.madhan.firebaseauthentication.presentation.login

import androidx.lifecycle.viewModelScope
import com.madhan.firebaseauthentication.domain.use_case.AuthUseCases
import com.madhan.firebaseauthentication.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.SideEffect>() {

    override fun setInitialState() = LoginContract.State()

    override suspend fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> setState { copy(email = event.email, error = null) }
            is LoginContract.Event.PasswordChanged -> setState { copy(password = event.password, error = null) }
            LoginContract.Event.Login -> login()
            LoginContract.Event.SignUp -> setSideEffect(LoginContract.SideEffect.NavigateToSignUp)
            LoginContract.Event.ForgotPassword -> setSideEffect(LoginContract.SideEffect.NavigateToForgotPassword)
        }
    }

    private fun login() {
        val email = viewState.value.email
        val password = viewState.value.password

        if (email.isBlank() || password.isBlank()) {
            setState { copy(error = "Fields are mandatory") }
            return
        }

        viewModelScope.launch {
            authUseCases.login(email, password)
                .onStart { setState { copy(isLoading = true, error = null) } }
                .catch { e -> setState { copy(isLoading = false, error = e.message) } }
                .collect { result ->
                    setState { copy(isLoading = false) }
                    if (result.isSuccess) {
                        setSideEffect(LoginContract.SideEffect.NavigateToHome)
                    } else {
                        setState { copy(error = result.exceptionOrNull()?.message) }
                    }
                }
        }
    }
}
