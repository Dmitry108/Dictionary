package ru.dim.dictionary.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.dim.core.interactor.IDataInteractor
import ru.dim.dictionary.R
import ru.dim.dictionary.app.DictionaryApp
import ru.dim.dictionary.ulils.isOnline
import ru.dim.model.ViewState

class DescriptionViewModel(private val interactor: IDataInteractor<ViewState>) :
    ru.dim.core.base.BaseViewModel<ViewState>() {

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