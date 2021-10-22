package com.example.weatherv2.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel

abstract class BaseViewModel<I> : ViewModel() {
    val intent = Channel<I>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    protected abstract fun handleIntent()


}