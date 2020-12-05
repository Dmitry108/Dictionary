package ru.dim.dictionary.interactor

import ru.dim.dictionary.model.entity.SearchResult

interface IDataInteractor<T> {
    var currentResult: SearchResult?
    suspend fun getData(word: String, isOnline: Boolean): T
    suspend fun getAll(): T
}