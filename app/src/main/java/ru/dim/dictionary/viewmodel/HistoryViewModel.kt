package ru.dim.dictionary.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.dictionary.interactor.IDataInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.entity.SearchResult

class HistoryViewModel (private val interactor: IDataInteractor<ViewState>) : BaseViewModel<ViewState>() {

    @ExperimentalCoroutinesApi
    override fun handleError(throwable: Throwable) {
        coroutineScope.launch {
            viewModelChannel.send(ViewState.Error(throwable))
        }
    }

    @ExperimentalCoroutinesApi
    fun getAllData() {
        stopJobs()
        coroutineScope.launch (Dispatchers.IO) {
            viewModelChannel.send(ViewState.Loading(null))
            viewModelChannel.send(interactor.getAll())
        }
    }

    fun saveCurrentResult(searchResult: SearchResult) {
        interactor.currentResult = searchResult
    }
}