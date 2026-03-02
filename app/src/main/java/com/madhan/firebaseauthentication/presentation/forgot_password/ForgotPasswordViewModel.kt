package com.madhan.firebaseauthentication.presentation.forgot_password

import androidx.lifecycle.viewModelScope
import com.madhan.firebaseauthentication.domain.use_case.AuthUseCases
import com.madhan.firebaseauthentication.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel<ForgotPasswordContract.State, ForgotPasswordContract.Event, ForgotPasswordContract.SideEffect>() {

    override fun setInitialState() = ForgotPasswordContract.State()

    override suspend fun handleEvents(event: ForgotPasswordContract.Event) {
        when (event) {
            is ForgotPasswordContract.Event.EmailChanged -> setState { copy(email = event.email, error = null) }
            ForgotPasswordContract.Event.SendResetLink -> sendResetLink()
            ForgotPasswordContract.Event.BackToLogin -> setSideEffect(ForgotPasswordContract.SideEffect.NavigateBack)
        }
    }

    private fun sendResetLink() {
        val email = viewState.value.email
        if (email.isBlank()) {
            setState { copy(error = "Enter email") }
            return
        }

        viewModelScope.launch {
            authUseCases.forgotPassword(email)
                .onStart { setState { copy(isLoading = true, error = null) } }
                .catch { e -> setState { copy(isLoading = false, error = e.message) } }
                .collect { result ->
                    setState { copy(isLoading = false) }
                    if (result.isSuccess) {
                        setState { copy(isEmailSent = true) }
                    } else {
                        setState { copy(error = result.exceptionOrNull()?.message) }
                    }
                }
        }
    }
}
