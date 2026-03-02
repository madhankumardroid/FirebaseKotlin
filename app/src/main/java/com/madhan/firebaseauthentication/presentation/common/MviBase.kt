package com.madhan.firebaseauthentication.presentation.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

interface ViewState
interface ViewEvent
interface ViewSideEffect

abstract class BaseViewModel<S : ViewState, E : ViewEvent, SE : ViewSideEffect> : ViewModel() {

    private val initialState: S by lazy { setInitialState() }

    private val _viewState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()

    private val _sideEffect: MutableSharedFlow<SE> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    protected abstract fun setInitialState(): S

    fun onEvent(event: E) {
        viewModelScope.launch {
            handleEvents(event)
        }
    }

    protected abstract suspend fun handleEvents(event: E)

    protected fun setState(reducer: S.() -> S) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setSideEffect(sideEffect: SE) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }
}
