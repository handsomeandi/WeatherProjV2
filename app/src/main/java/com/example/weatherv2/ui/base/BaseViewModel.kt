package com.example.weatherv2.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel<I, S> : ViewModel() {
    val intent = Channel<I>(Channel.UNLIMITED)
    protected abstract val _state: MutableStateFlow<S>

    protected abstract fun handleIntent()

    @Suppress("UNCHECKED_CAST")
    protected abstract fun updateUIState(update: suspend S.() -> Unit)
}