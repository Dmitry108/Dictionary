package ru.dim.dictionary.contract

import io.reactivex.Observable
import ru.dim.dictionary.model.ViewState

interface IView {
    fun renderData(viewState: ViewState)
}

interface IPresenter<T: ViewState, V: IView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}

interface IInteractor<T> {
    fun getData(word: String, isOnline: Boolean): Observable<T>
}

interface IRepository<T> {
    fun getData(word: String): Observable<T>
}

interface IDataSource<T> {
    fun getData(word: String): Observable<T>
}