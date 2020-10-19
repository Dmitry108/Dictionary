package ru.dim.dictionary.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.entity.SearchResult

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

    private fun вытащитьДанныеИзТогоЧтоПридет(тоЧтоПришло: List<SearchResult>): ViewState {
        return ViewState.Success(тоЧтоПришло)
    }
    private fun getObserver(): DisposableObserver<ViewState> =
            object : DisposableObserver<ViewState>() {
                override fun onNext(viewState: ViewState) {
                    liveData.value = viewState
                }
                override fun onError(e: Throwable) {
                    liveData.value = ViewState.Error(e)
                }
                override fun onComplete() { }
            }

    override fun handleError(throwable: Throwable) {
        liveData.value = ViewState.Error(throwable)
    }

    override fun onCleared() {
        liveData.value = ViewState.Success(null)
        super.onCleared()
    }
}