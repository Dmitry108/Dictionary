package ru.dim.dictionary.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.core.interactor.IDataInteractor
import ru.dim.dictionary.ulils.isOnline
import ru.dim.model.ViewState
import ru.dim.model.entity.SearchResult

class MainViewModel (
    private val interactor: IDataInteractor<ViewState>
) : ru.dim.core.base.BaseViewModel<ViewState>(){

    @ExperimentalCoroutinesApi
    fun getData(word: String, isOnline: Boolean) {
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

    fun saveCurrentResult(data: SearchResult) {
        interactor.currentResult = data
    }

    @ExperimentalCoroutinesApi
    fun showData() {
        getData(interactor.currentResult?.text ?: "", isOnline())
    }
}