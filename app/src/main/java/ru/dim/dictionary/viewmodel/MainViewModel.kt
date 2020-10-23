package ru.dim.dictionary.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.ViewState

class MainViewModel (
    private val interactor: MainInteractor
) : BaseViewModel<ViewState>(){

    @ExperimentalCoroutinesApi
    override fun getData(word: String, isOnline: Boolean) {
        stopJobs()
        coroutineScope.launch (Dispatchers.IO) {
            viewModelChannel.send(ViewState.Loading(null))
            viewModelChannel.send(interactor.getData(word, isOnline))
        }
    }

    @ExperimentalCoroutinesApi
    override fun handleError(throwable: Throwable) {
        coroutineScope.launch {
            viewModelChannel.send(ViewState.Error(throwable))
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        coroutineScope.launch {
            viewModelChannel.send(ViewState.Success(null))
        }
        super.onCleared()
    }
}