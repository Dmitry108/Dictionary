package ru.dim.dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.dim.dictionary.model.ViewState

abstract class BaseViewModel<T: ViewState> : ViewModel() {

    @ExperimentalCoroutinesApi
    protected val viewModelChannel: BroadcastChannel<T> = BroadcastChannel(Channel.Factory.CONFLATED)
    protected val coroutineScope = viewModelScope.plus(
        CoroutineExceptionHandler { _, throwable -> handleError(throwable) })

    abstract fun getData(word: String, isOnline: Boolean)
    abstract fun handleError(throwable: Throwable)

    protected fun stopJobs() {
        coroutineScope.coroutineContext.cancelChildren()
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelChannel.close()
        stopJobs()
    }

    @ExperimentalCoroutinesApi
    fun getChannel(): ReceiveChannel<T> = viewModelChannel.openSubscription()
}