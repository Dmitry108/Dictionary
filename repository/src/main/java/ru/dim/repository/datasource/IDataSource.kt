package ru.dim.repository.datasource

interface IDataSource<T> {
    suspend fun getData(word: String): T
}