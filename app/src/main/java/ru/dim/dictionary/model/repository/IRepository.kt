package ru.dim.dictionary.model.repository

import io.reactivex.Observable

interface IRepository<T> {
    fun getData(word: String): Observable<T>
}