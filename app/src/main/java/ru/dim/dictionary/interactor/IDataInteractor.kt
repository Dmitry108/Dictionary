package ru.dim.dictionary.interactor

interface IDataInteractor<T> {
    suspend fun getData(word: String, isOnline: Boolean): T
}