package ru.dim.dictionary.interactor

import io.reactivex.Observable

interface IInteractor<T> {
    fun getData(word: String, isOnline: Boolean): Observable<T>
}