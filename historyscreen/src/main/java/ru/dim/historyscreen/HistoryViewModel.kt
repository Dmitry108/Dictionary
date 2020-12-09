package ru.dim.historyscreen

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.core.interactor.IDataInteractor
import ru.dim.model.ViewState
import ru.dim.model.entity.SearchResult

class HistoryViewModel (private val interactor: IDataInteractor<ViewState>) : ru.dim.core.base.BaseViewModel<ViewState>() {

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