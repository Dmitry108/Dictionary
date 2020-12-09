package ru.dim.core.interactor

import ru.dim.model.entity.SearchResult

interface IDataInteractor<T> {
    var currentResult: SearchResult?
    suspend fun getData(word: String, isOnline: Boolean): T
    suspend fun getAll(): T
}