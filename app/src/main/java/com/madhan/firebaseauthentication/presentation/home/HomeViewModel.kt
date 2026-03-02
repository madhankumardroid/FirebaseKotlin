package com.madhan.firebaseauthentication.presentation.home

import androidx.lifecycle.viewModelScope
import com.madhan.firebaseauthentication.domain.use_case.AuthUseCases
import com.madhan.firebaseauthentication.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.SideEffect>() {

    override fun setInitialState() = HomeContract.State()

    init {
        onEvent(HomeContract.Event.LoadUserData)
    }

    override suspend fun handleEvents(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.LoadUserData -> loadUserData()
            HomeContract.Event.Logout -> logout()
        }
    }

    private fun loadUserData() {
        val currentUser = authUseCases.getCurrentUser()
        if (currentUser != null) {
            viewModelScope.launch {
                authUseCases.getUserData(currentUser.uid)
                    .onStart { setState { copy(isLoading = true, error = null) } }
                    .catch { e -> setState { copy(isLoading = false, error = e.message) } }
                    .collect { result ->
                        setState { copy(isLoading = false) }
                        if (result.isSuccess) {
                            setState { copy(user = result.getOrNull()) }
                        } else {
                            setState { copy(error = result.exceptionOrNull()?.message) }
                        }
                    }
            }
        }
    }

    private fun logout() {
        authUseCases.logout()
        setSideEffect(HomeContract.SideEffect.NavigateToLogin)
    }
}
