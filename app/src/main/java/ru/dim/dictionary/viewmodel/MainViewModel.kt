package ru.dim.dictionary.viewmodel

import androidx.lifecycle.LiveData
import io.reactivex.observers.DisposableObserver
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.datasource.database.DataSourceLocal
import ru.dim.dictionary.model.datasource.server.DataSourceRemote
import ru.dim.dictionary.model.repository.RepositoryImplementation

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal()))
) : BaseViewModel<ViewState>(){

    //private var viewState: ViewState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<ViewState> {
            compositeDisposable.add(
                interactor.getData(word, isOnline)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe {
                        liveData.value = ViewState.Loading(null) }
                    .subscribeWith(getObserver())
            )
            return liveData
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
}
