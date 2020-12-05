package ru.dim.dictionary.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.dictionary.R
import ru.dim.dictionary.app.DictionaryApp
import ru.dim.dictionary.interactor.IDataInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.utils.isOnline

class DescriptionViewModel(private val interactor: IDataInteractor<ViewState>) :
    BaseViewModel<ViewState>() {

    @ExperimentalCoroutinesApi
    override fun handleError(throwable: Throwable) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModelChannel.send(ViewState.Error(throwable))
        }
    }

    @ExperimentalCoroutinesApi
    fun showData() {
        stopJobs()
        coroutineScope.launch(Dispatchers.IO) {
            if (isOnline()) {
                viewModelChannel.send(ViewState.Loading(null))
                viewModelChannel.send(ViewState.Success(interactor.currentResult))
            } else {
                handleError(Throwable(DictionaryApp.getInstance().resources.getString(R.string.internetIsNotAvailable)))
            }
        }
    }
}