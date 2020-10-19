package ru.dim.dictionary.model.datasource

interface IDataSource<T> {
    suspend fun getData(word: String): T
}