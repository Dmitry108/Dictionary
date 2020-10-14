package ru.dim.dictionary.depricated

import ru.dim.dictionary.model.ViewState

interface IPresenter<T: ViewState, V: IView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}