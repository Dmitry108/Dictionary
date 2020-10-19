package ru.dim.dictionary.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.ViewState

class MainViewModel (
    private val interactor: MainInteractor
) : BaseViewModel<ViewState>(){

    override fun getData(word: String, isOnline: Boolean): LiveData<ViewState> {
        liveData.value = ViewState.Loading(null)
        stopJobs()
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                liveData.postValue(interactor.getData(word, isOnline))
            }
        }
        return liveData
    }

    override fun handleError(throwable: Throwable) {
        liveData.value = ViewState.Error(throwable)
    }

    override fun onCleared() {
        liveData.value = ViewState.Success(null)
        super.onCleared()
    }
}