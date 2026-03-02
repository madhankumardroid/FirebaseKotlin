package com.madhan.firebaseauthentication.presentation.signup

import androidx.lifecycle.viewModelScope
import com.madhan.firebaseauthentication.domain.use_case.AuthUseCases
import com.madhan.firebaseauthentication.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel<SignUpContract.State, SignUpContract.Event, SignUpContract.SideEffect>() {

    override fun setInitialState() = SignUpContract.State()

    override suspend fun handleEvents(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.FirstNameChanged -> setState { copy(firstName = event.firstName, error = null) }
            is SignUpContract.Event.LastNameChanged -> setState { copy(lastName = event.lastName, error = null) }
            is SignUpContract.Event.EmailChanged -> setState { copy(email = event.email, error = null) }
            is SignUpContract.Event.PasswordChanged -> setState { copy(password = event.password, error = null) }
            SignUpContract.Event.SignUp -> signUp()
            SignUpContract.Event.BackToLogin -> setSideEffect(SignUpContract.SideEffect.NavigateBack)
        }
    }

    private fun signUp() {
        val state = viewState.value
        if (state.firstName.isBlank() || state.lastName.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            setState { copy(error = "Fields are mandatory") }
            return
        }

        viewModelScope.launch {
            authUseCases.signUp(state.email, state.password, state.firstName, state.lastName)
                .onStart { setState { copy(isLoading = true, error = null) } }
                .catch { e -> setState { copy(isLoading = false, error = e.message) } }
                .collect { result ->
                    setState { copy(isLoading = false) }
                    if (result.isSuccess) {
                        setSideEffect(SignUpContract.SideEffect.NavigateToHome)
                    } else {
                        setState { copy(error = result.exceptionOrNull()?.message) }
                    }
                }
        }
    }
}
