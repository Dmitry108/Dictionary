package ru.dim.dictionary.interactor

interface IInteractor<T> {
    suspend fun getData(word: String, isOnline: Boolean): T
}