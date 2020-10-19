package ru.dim.dictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.dim.dictionary.model.ViewState

abstract class BaseViewModel<T: ViewState> (
    protected val liveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {
    protected val coroutineScope = CoroutineScope(
        Dispatchers.Main
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
                + SupervisorJob()
    )

    abstract fun getData(word: String, isOnline: Boolean): LiveData<T>
    abstract fun handleError(throwable: Throwable)

    protected fun stopJobs() {
        coroutineScope.coroutineContext.cancelChildren()
    }
    override fun onCleared() {
        super.onCleared()
        stopJobs()
    }
    fun getLiveData(): LiveData<T> = liveData
}